package com.wulingqi.lightning.portal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.mapper.MemberMapper;
import com.wulingqi.lightning.mapper.SmsCodeMapper;
import com.wulingqi.lightning.mapper.TeamLevelMapper;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.SmsCode;
import com.wulingqi.lightning.model.TeamLevel;
import com.wulingqi.lightning.portal.domain.MemberDetails;
import com.wulingqi.lightning.portal.dto.EditPasswordDto;
import com.wulingqi.lightning.portal.dto.ForgetPasswordDto;
import com.wulingqi.lightning.portal.dto.LoginDto;
import com.wulingqi.lightning.portal.dto.ModifyPayPasswordDto;
import com.wulingqi.lightning.portal.dto.RegisterDto;
import com.wulingqi.lightning.portal.mapper.PortalMapper;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.portal.util.JwtTokenUtil;
import com.wulingqi.lightning.portal.vo.LoginVo;
import com.wulingqi.lightning.portal.vo.MemberInfoVo;
import com.wulingqi.lightning.utils.LightningConstant;
import com.wulingqi.lightning.utils.StringUtils;

/**
 * 会员管理Service实现类
 */
@Service
public class MemberServiceImpl implements MemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
	
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private MemberService memberService;
    
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
    private SmsCodeMapper smsCodeMapper;
    
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
    	return portalMemberMapper.selectMemberByPhone(username);
    }

    @Override
    public  CommonResult<LoginVo> register(RegisterDto requestDto) {
    	
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
        
        //查询是否已有该用户
        Member member = portalMemberMapper.selectMemberByPhone(requestDto.getPhone());
        if (member != null) {
            return CommonResult.failed("该用户已经存在");
        }
        
        Member inviter = null;
        if(!"123456".equals(requestDto.getInviteCode())) {
        	inviter = portalMemberMapper.selectMemberByInviteCode(requestDto.getInviteCode());
            if(inviter == null) {
            	return CommonResult.failed("邀请码不存在");
            }
        }
        
        /*
        Member inviter = null;
        if(!StringUtils.isEmpty(requestDto.getInviteCode())) {
        	inviter = portalMemberMapper.selectMemberByInviteCode(requestDto.getInviteCode());
            if(inviter == null) {
            	return CommonResult.failed("邀请码不存在");
            }
        }
        */
        
        member = new Member();
        //没有该用户进行添加操作
        member.setUsername(requestDto.getPhone());
        member.setPhone(requestDto.getPhone());
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        member.setCreateTime(new Date());
        member.setIntegration(0);
        member.setGender(0); //性别：0->未知；1->男；2->女
        member.setStatus(LightningConstant.STATUS_ENABLE); //帐号启用状态: 1->启用
        
        member.setInviterId(inviter == null ? null : inviter.getId());
        
        StringBuilder num = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i < 4; i++) {
			num.append(random.nextInt(10));
		}
		
		member.setNickname("用户" + num.toString());
        
        //用字符数组的方式随机
  		StringBuilder inviteCode = new StringBuilder();
  		String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  		char[] m = model.toCharArray();
  		
  		for(int i=0; i<6; i++ ) {
  			char c = m[(int)(Math.random() * 26)];
  			inviteCode.append(c);
  		}
  		
  		Member exit = portalMemberMapper.selectMemberByInviteCode(inviteCode.toString());
  		while (exit != null) {
  			inviteCode.delete(0, inviteCode.length());
  			for(int i=0; i<6; i++ ) {
  				char c = m[(int)(Math.random() * 26)];
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
    			teamLevel.setAllParentId(inviterTeamLevel.getAllParentId() + inviter.getId() + "/");
    		}
    		teamLevelMapper.insert(teamLevel);
    		//保存团队层级关系表----end
    		
        }
        
        //更新会员信息
        memberMapper.updateByPrimaryKey(member);
        
        String token = jwtTokenUtil.generateToken(member.getPhone());
        
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setTokenHead(tokenHead);
        
        return CommonResult.success(loginVo);
        
    }
    
    @Override
    public CommonResult<LoginVo> login(LoginDto requestDto) {
    	
    	//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getPhone()) ||
    			StringUtils.isEmpty(requestDto.getPassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	Member member = portalMemberMapper.selectMemberByPhone(requestDto.getPhone());
    	
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
    public CommonResult<String> getAuthCode(String phone) {
    	
    	//查询1分钟内发送的短信
    	List<SmsCode> list = portalMapper.selectSmsCodeByPhoneAndMinute(phone, 1);
    	if(!list.isEmpty()) {
    		return CommonResult.failed("操作频繁，请稍后再试");
    	}
    	
    	//查询30分钟内发送的短信
    	list = portalMapper.selectSmsCodeByPhoneAndMinute(phone, 30);
    	if(list.size() >= 5) {
    		return CommonResult.failed("操作频繁，请稍后再试");
    	}
    	
    	//查询1天内发送的短信
    	list = portalMapper.selectSmsCodeByPhoneAndMinute(phone, 24 * 60);
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
        
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        smsCode.setContent(code.toString());
        smsCode.setCreateTime(new Date());
        smsCodeMapper.insert(smsCode);
        
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + phone, code.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + phone, AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(null, "获取验证码成功");
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
    
	/**
     * 修改支付密码
     */
	@Override
	public CommonResult<String> modifyPayPassword(ModifyPayPasswordDto requestDto) {
		//判断输入参数是否为空
    	if(StringUtils.isEmpty(requestDto.getAuthCode()) ||
    			StringUtils.isEmpty(requestDto.getPassword()) ||
    			StringUtils.isEmpty(requestDto.getRePassword())) {
    		return CommonResult.failed(LightningConstant.SERVER_ERROR);
    	}
    	
    	if(!requestDto.getPassword().equals(requestDto.getRePassword())) {
    		return CommonResult.failed("两次输入密码不一致");
    	}
    	
    	Member member = memberMapper.selectByPrimaryKey(memberService.getCurrentMember().getId());
    	
        //验证验证码
        if(!verifyAuthCode(requestDto.getAuthCode(), member.getPhone())){
            return CommonResult.failed("验证码错误");
        }
        
        //md5(md5($str).md5("tbcc"));
        String payPassword = DigestUtils.md5DigestAsHex(
				(DigestUtils.md5DigestAsHex(requestDto.getPassword().getBytes()) + 
  				DigestUtils.md5DigestAsHex("tbcc".getBytes())).getBytes());
  		
  		member.setPayPassword(payPassword);
        memberMapper.updateByPrimaryKey(member);
		
		return CommonResult.success(null, "修改成功");
	}

	@Override
	public MemberInfoVo getMemberInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
