package com.wulingqi.lightning.portal.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.mapper.MemberMapper;
import com.wulingqi.lightning.mapper.MemberStatisticsMapper;
import com.wulingqi.lightning.mapper.StatisticsHandleQueueMapper;
import com.wulingqi.lightning.mapper.TeamLevelMapper;
import com.wulingqi.lightning.mapper.VerificationCodeMapper;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.MemberStatistics;
import com.wulingqi.lightning.model.StatisticsHandleQueue;
import com.wulingqi.lightning.model.TeamLevel;
import com.wulingqi.lightning.model.VerificationCode;
import com.wulingqi.lightning.portal.domain.MemberDetails;
import com.wulingqi.lightning.portal.dto.AuthCodeDto;
import com.wulingqi.lightning.portal.dto.EditPasswordDto;
import com.wulingqi.lightning.portal.dto.ForgetPasswordDto;
import com.wulingqi.lightning.portal.dto.LoginDto;
import com.wulingqi.lightning.portal.dto.RegisterDto;
import com.wulingqi.lightning.portal.dto.TeamInfoDto;
import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.portal.util.JwtTokenUtil;
import com.wulingqi.lightning.portal.util.RegexpUtils;
import com.wulingqi.lightning.portal.vo.LoginVo;
import com.wulingqi.lightning.portal.vo.MemberInfoVo;
import com.wulingqi.lightning.portal.vo.TeamInfoVo;
import com.wulingqi.lightning.portal.vo.TeamListVo;
import com.wulingqi.lightning.utils.LightningConstant;
import com.wulingqi.lightning.utils.StringUtils;

/**
 * 会员管理Service实现类
 */
@Service
public class MemberServiceImpl implements MemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private CommonService commonService;
	
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RedisService redisService;
    
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private PortalMemberMapper portalMemberMapper;
    
    @Autowired
    private TeamLevelMapper teamLevelMapper;
    
    @Autowired
    private PortalMapper portalMapper;
    
    @Autowired
    private VerificationCodeMapper verificationCodeMapper;
    
    @Autowired
    private MemberStatisticsMapper memberStatisticsMapper;
    
    @Autowired
    private StatisticsHandleQueueMapper statisticsHandleQueueMapper;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    /**
     * 根据memberId获取会员
     */
	@Override
	public Member getMemberById(Long memberId) {
		return memberMapper.selectByPrimaryKey(memberId);
	}
    
	/**
     * 根据用户名获取会员
     */
    @Override
    public Member getByUsername(String username) {
    	return portalMemberMapper.selectMemberByAccount(username);
    }

    @Override
    public  CommonResult<LoginVo> register(RegisterDto requestDto) {
    	
    	//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getAccount()) ||
    			StringUtils.isEmpty(requestDto.getAuthCode()) ||
    			StringUtils.isEmpty(requestDto.getPassword()) ||
    			StringUtils.isEmpty(requestDto.getRePassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	if(!requestDto.getPassword().equals(requestDto.getRePassword())) {
    		return CommonResult.failed("两次输入密码不一致");
    	}
    	
        //验证验证码
        if(!verifyAuthCode(requestDto.getAuthCode(), requestDto.getAccount())){
            return CommonResult.failed("验证码错误");
        }
        
        //查询是否已有该用户
        Member member = portalMemberMapper.selectMemberByAccount(requestDto.getAccount());
        if (member != null) {
            return CommonResult.failed("用户已存在，请直接登录");
        }
        
        Member inviter = null;
        BigDecimal agentRatio = null;
        if(!"000000".equals(requestDto.getInviteCode())) {
        	inviter = portalMemberMapper.selectMemberByInviteCode(requestDto.getInviteCode());
            if(inviter == null) {
            	return CommonResult.failed("邀请码不存在");
            } else {
            	
            	if(LightningConstant.STATUS_DISABLE.equals(inviter.getStatus())) {
            		return CommonResult.failed("邀请人已被封号");
            	}
            	
            	/**
            	 * 判断代理比例是否在邀请人分享时设置的范围
            	 * 代理比例必须小于等于邀请人的代理比例并且不能小于等于0
            	 */
            	if(StringUtils.isEmpty(inviter.getAgentRatio())) {
            		return CommonResult.failed(LightningConstant.SERVER_ERROR);
            	}
            	BigDecimal inviterAgentRatio = new BigDecimal(inviter.getAgentRatio());
            	agentRatio = new BigDecimal(requestDto.getAgentRatio());
            	
            	if(agentRatio.compareTo(inviterAgentRatio) > 0
            			|| agentRatio.compareTo(new BigDecimal("0")) < 1) {
            		return CommonResult.failed(LightningConstant.SERVER_ERROR);
            	}
            	
            }
        } else {
        	agentRatio = new BigDecimal(commonService.getDictionaryValue(LightningConstant.MEMBER_AGENT_RATIO));
        }
        
        Date currentDate = new Date();
        member = new Member();
        //没有该用户进行添加操作
        member.setUsername(null);
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        member.setEmail(null);
        member.setPhone(requestDto.getAccount());
        member.setCreateTime(currentDate);
        member.setIntegration("0.00");
        member.setFreezeIntegration("0.00");
        member.setGender(0); //性别：0->未知；1->男；2->女
        member.setStatus(LightningConstant.STATUS_ENABLE); //帐号启用状态: 1->启用
        member.setEffectiveStatus(LightningConstant.EFFECTIVE_STATUS_NO); //有效会员状态: 0->无效
        
        member.setInviterId(inviter == null ? null : inviter.getId());
        member.setAgentRatio(agentRatio.toPlainString());
        member.setVersion(1L);
        
        StringBuilder num = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i < 4; i++) {
			num.append(random.nextInt(10));
		}
		
		member.setNickname("用户" + num.toString());
        
        //用字符数组的方式随机
  		StringBuilder inviteCode = new StringBuilder();
  		String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
  		char[] m = model.toCharArray();
  		
  		for(int i=0; i<6; i++ ) {
  			char c = m[(int)(Math.random() * 35)];
  			inviteCode.append(c);
  		}
  		
  		Member exit = portalMemberMapper.selectMemberByInviteCode(inviteCode.toString());
  		while (exit != null) {
  			inviteCode.delete(0, inviteCode.length());
  			for(int i=0; i<6; i++ ) {
  				char c = m[(int)(Math.random() * 35)];
  				inviteCode.append(c);
  			}
  			
  			exit = portalMemberMapper.selectMemberByInviteCode(inviteCode.toString());
  		}
  		member.setInviteCode(inviteCode.toString());
        memberMapper.insert(member);
        
        //保存团队层级关系表
        if(inviter != null) {
        	
        	//保存团队层级关系表----start
            TeamLevel teamLevel = new TeamLevel();
    		teamLevel.setMemberId(member.getId());
    		teamLevel.setParentId(inviter.getId());
    		
    		TeamLevel inviterTeamLevel = portalMemberMapper.selectTeamLevelByMemberId(inviter.getId());
    		if(inviterTeamLevel == null || StringUtils.isEmpty(inviterTeamLevel.getAllParentId())) {
    			teamLevel.setAllParentId("/" + inviter.getId() + "/");
    		} else {
    			teamLevel.setAllParentId("/"  + inviter.getId() +  inviterTeamLevel.getAllParentId());
    		}
    		teamLevelMapper.insert(teamLevel);
    		//保存团队层级关系表----end
    		
    		//写入邀请统计处理队列表
			StatisticsHandleQueue statisticsHandleQueue = new StatisticsHandleQueue();
			statisticsHandleQueue.setMemberId(member.getId());
			statisticsHandleQueue.setHandleType(LightningConstant.HANDLE_TYPE_INVITE); //处理类型: 0->邀请统计
			statisticsHandleQueue.setHandleStatus(LightningConstant.HANDLE_STATUS_NO); //处理状态: 0->未处理
			statisticsHandleQueue.setCreateTime(currentDate);
			statisticsHandleQueueMapper.insert(statisticsHandleQueue);
    		
        }
        
        //初始化会员统计信息表
        MemberStatistics memberStatistics = new MemberStatistics();
        memberStatistics.setMemberId(member.getId());
        memberStatistics.setInviteCount(0);
        memberStatistics.setTeamCount(0);
        memberStatistics.setInviteEffectiveCount(0);
        memberStatistics.setTeamEffectiveCount(0);
        memberStatistics.setAgentIncome("0.00");
        memberStatisticsMapper.insert(memberStatistics);
        
        String token = jwtTokenUtil.generateToken(member.getPhone());
        
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setTokenHead(tokenHead);
        loginVo.setAlipayBindingStatus("0");
        
        return CommonResult.success(loginVo);
        
    }
    
    @Override
    public CommonResult<LoginVo> login(LoginDto requestDto) {
    	
    	//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getAccount()) ||
    			StringUtils.isEmpty(requestDto.getPassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	Member member = portalMemberMapper.selectMemberByAccount(requestDto.getAccount());
    	
    	if(member == null) {
    		return CommonResult.failed("用户名或密码错误");
    	}
    	
    	if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
    		return CommonResult.failed("用户名或密码错误");
    	}
    	
    	if(member.getStatus().equals(LightningConstant.STATUS_DISABLE)) {
    		return CommonResult.failed("账号已被冻结");
    	}
        
    	String token = jwtTokenUtil.generateToken(member.getPhone());
    	
    	LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setTokenHead(tokenHead);
        
        if(StringUtils.isEmpty(member.getAvatarUrl())) {
        	loginVo.setAlipayBindingStatus("0");
        } else {
        	loginVo.setAlipayBindingStatus("1");
        }
         
        return CommonResult.success(loginVo);
    	
    }
    
    /**
     * 忘记密码
     */
	@Override
	public CommonResult<String> forgetPassword(ForgetPasswordDto requestDto) {
		
		//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getPhone()) ||
    			StringUtils.isEmpty(requestDto.getAuthCode()) ||
    			StringUtils.isEmpty(requestDto.getPassword()) ||
    			StringUtils.isEmpty(requestDto.getRePassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	if(!requestDto.getPassword().equals(requestDto.getRePassword())) {
    		return CommonResult.failed("两次输入密码不一致");
    	}
    	
        //验证验证码
        if(!verifyAuthCode(requestDto.getAuthCode(), requestDto.getPhone())){
            return CommonResult.failed("验证码错误");
        }
        
        Member member = getByUsername(requestDto.getPhone());
        if(member == null) {
        	return CommonResult.failed(LightningConstant.SERVER_ERROR);
        }
        
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        memberMapper.updateByPrimaryKey(member);
		
		return CommonResult.success(null, "找回成功");
	}
	
	/**
     * 忘记密码
     */
	@Override
	public CommonResult<String> editPassword(EditPasswordDto requestDto) {
		//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getOldPassword()) ||
    			StringUtils.isEmpty(requestDto.getPassword()) ||
    			StringUtils.isEmpty(requestDto.getRePassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	if(!requestDto.getPassword().equals(requestDto.getRePassword())) {
    		return CommonResult.failed("两次输入密码不一致");
    	}
    	
    	Member member = getMemberById(getCurrentMember().getId());
    	if(!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())){
    		return CommonResult.failed("旧密码错误");
    	}
    	
    	member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        memberMapper.updateByPrimaryKey(member);
    	
    	return CommonResult.success(null, "修改成功");
	}
    
    @Override
    public CommonResult<String> getAuthCode(AuthCodeDto requestDto) {
    	
    	String receiver = requestDto.getReceiver();
    	Integer verifyType = requestDto.getVerifyType();
    	if(StringUtils.isEmpty(receiver) || verifyType == null) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	if(!RegexpUtils.checkPhone(receiver)) {
    		return CommonResult.failed("手机号格式错误");
    	}
    	
    	Member member = portalMemberMapper.selectMemberByAccount(receiver);
    	if(LightningConstant.VERIFY_TYPE_REGISTER.equals(verifyType)) {
    		if(member != null) {
    			return CommonResult.failed("用户已存在，请直接登录");
    		}
    	} else if(LightningConstant.VERIFY_TYPE_EDIT_PW.equals(verifyType)
    			|| LightningConstant.VERIFY_TYPE_FORGET_PW.equals(verifyType)) {
    		if(member == null) {
    			return CommonResult.failed("用户不存在");
    		}
    	} else {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	//查询1分钟内发送的短信
    	List<VerificationCode> list = portalMapper.selectVerificationCodeByReceiverAndMinute(receiver, 1);
    	if(!list.isEmpty()) {
    		return CommonResult.failed("操作频繁，请稍后再试");
    	}
    	
    	//查询60分钟内发送的短信
    	list = portalMapper.selectVerificationCodeByReceiverAndMinute(receiver, 60);
    	if(list.size() >= 3) {
    		return CommonResult.failed("操作频繁，请稍后再试");
    	}
    	
    	//查询1天内发送的短信
    	list = portalMapper.selectVerificationCodeByReceiverAndMinute(receiver, 24 * 60);
    	if(list.size() >= 10) {
    		return CommonResult.failed("操作频繁，请稍后再试");
    	}
    	
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6; i++){
        	code.append(random.nextInt(10));
        }
        //验证码绑定手机号并存储到redis
        try {
//			boolean flag = sendSms(phone, code.toString());
//			if(!flag) {
//				return CommonResult.failed("操作频繁，请稍后再试");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommonResult.failed("获取验证码失败，请稍后再试");
		}
        
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setReceiver(receiver);
        verificationCode.setReceiverType(LightningConstant.RECEIVER_TYPE_PHONE);
        verificationCode.setVerifyType(verifyType);
        verificationCode.setContent(code.toString());
        verificationCode.setCreateTime(new Date());
        verificationCodeMapper.insert(verificationCode);
        
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + receiver, code.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + receiver, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(code.toString(), "获取验证码成功");
    }
    

    @Override
    public Member getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = null;
        try {
        	memberDetails = (MemberDetails) auth.getPrincipal();
        	return memberDetails.getMember();
		} catch (Exception e) {
			
		}
        return null;
    }

    //对输入的验证码进行校验
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }

	@Override
	public MemberInfoVo getMemberInfo() {
		MemberInfoVo memberInfoVo = new MemberInfoVo();
		Member member = getMemberById(getCurrentMember().getId());
		BeanUtils.copyProperties(member, memberInfoVo);
		memberInfoVo.setMemberId(member.getId().toString());
		memberInfoVo.setAccount(member.getPhone());
		
		if(member.getInviterId() != null) {
			Member inviter = getMemberById(member.getInviterId());
			if(StringUtils.isEmpty(inviter.getName())) {
				memberInfoVo.setInviter(inviter.getNickname());
			} else {
				memberInfoVo.setInviter(inviter.getName());
			}
		}
		memberInfoVo.setEffectiveStatus(String.valueOf(member.getEffectiveStatus()));
		memberInfoVo.setStatus(String.valueOf(member.getStatus()));
		
		BigDecimal integration = new BigDecimal(member.getIntegration());
		integration = integration.add(new BigDecimal(member.getFreezeIntegration()));
		
		memberInfoVo.setIntegration(integration.toPlainString());
		memberInfoVo.setAvailableIntegration(member.getIntegration());
		memberInfoVo.setFreezeIntegration(member.getFreezeIntegration());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		memberInfoVo.setCreateTime(df.format(member.getCreateTime()));
		
		return memberInfoVo;
	}
	
	/**
     * 获取我的团队信息
     */
	@Override
	public CommonResult<TeamInfoVo> getTeamInfo(TeamInfoDto requestDto) {
		
		Long memberId = getCurrentMember().getId();
		if(!StringUtils.isEmpty(requestDto.getPhone())) {
			Member lowerMember = portalMemberMapper.selectMemberByPhone(requestDto.getPhone());
			if(lowerMember != null) {
				String tempStr = "/"+ memberId +"/";
				TeamLevel teamLevel = portalMemberMapper.selectTeamLevelByMemberId(lowerMember.getId());
				if(teamLevel.getAllParentId().contains(tempStr)) {
					memberId = lowerMember.getId();
				}
			}
		}
		
		Member member = getMemberById(memberId);
		
		TeamInfoVo teamInfoVo = new TeamInfoVo();
		
		MemberStatistics memberStatistics = portalMemberMapper.selectMemberStatisticsByMemberId(memberId);
		
		teamInfoVo.setShareCount(String.valueOf(memberStatistics.getInviteCount()));
		teamInfoVo.setTeamCount(String.valueOf(memberStatistics.getTeamCount()));
		teamInfoVo.setShareEffectiveCount(String.valueOf(memberStatistics.getInviteEffectiveCount()));
		teamInfoVo.setTeamEffectiveCount(String.valueOf(memberStatistics.getTeamEffectiveCount()));
		teamInfoVo.setAvatarUrl(member.getAvatarUrl());
		teamInfoVo.setTotalIncome(memberStatistics.getAgentIncome());
		
		PageHelper.startPage(requestDto.getPage(), requestDto.getLimit());
		List<TeamListVo> result = portalMemberMapper.selectMemberTeamList(memberId);
		
		teamInfoVo.setTeamList(result);
		
		return CommonResult.success(teamInfoVo);
	}

}
