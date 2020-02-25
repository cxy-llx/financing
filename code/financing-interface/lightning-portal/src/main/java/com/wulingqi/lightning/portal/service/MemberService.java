package com.wulingqi.lightning.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.portal.dto.AuthCodeDto;
import com.wulingqi.lightning.portal.dto.CompleteInfoDto;
import com.wulingqi.lightning.portal.dto.EditPasswordDto;
import com.wulingqi.lightning.portal.dto.ForgetPasswordDto;
import com.wulingqi.lightning.portal.dto.LoginDto;
import com.wulingqi.lightning.portal.dto.RegisterDto;
import com.wulingqi.lightning.portal.dto.SharepageLinkDto;
import com.wulingqi.lightning.portal.dto.TeamInfoDto;
import com.wulingqi.lightning.portal.dto.UpdateAvatarUrlDto;
import com.wulingqi.lightning.portal.dto.UpdateNicknameDto;
import com.wulingqi.lightning.portal.vo.LoginVo;
import com.wulingqi.lightning.portal.vo.MemberInfoVo;
import com.wulingqi.lightning.portal.vo.SharepageLinkVo;
import com.wulingqi.lightning.portal.vo.TeamInfoVo;

/**
 * 会员管理Service
 */
public interface MemberService {
	
	/**
     * 根据memberId获取会员
     */
    Member getMemberById(Long memberId);
	
    /**
     * 根据用户名获取会员
     */
    Member getByUsername(String username);

    /**
     * 注册
     */
    @Transactional
    CommonResult<LoginVo> register(RegisterDto requestDto);
    
    /**
     * 登录
     */
    @Transactional
    CommonResult<LoginVo> login(LoginDto requestDto);
    
    /**
     * 忘记密码
     */
    @Transactional
    CommonResult<String> forgetPassword(ForgetPasswordDto requestDto);
    
    /**
     * 修改密码
     */
    @Transactional
    CommonResult<String> editPassword(EditPasswordDto requestDto);
    
    /**
     * 修改用户昵称
     */
    @Transactional
    CommonResult<String> updateNickname(UpdateNicknameDto requestDto);
    
    /**
     * 修改用户头像
     */
    @Transactional
    CommonResult<String> updateAvatarUrl(UpdateAvatarUrlDto requestDto);
    
    /**
     * 生成验证码
     */
    CommonResult<String> getAuthCode(AuthCodeDto requestDto);

    /**
     * 获取当前登录会员
     */
    Member getCurrentMember();

    MemberInfoVo getMemberInfo();
    
    /**
     * 获取我的团队信息
     */
    CommonResult<TeamInfoVo> getTeamInfo(TeamInfoDto requestDto);
    
    /**
     * 完善资料信息
     */
    @Transactional
    CommonResult<String> completeInfo(CompleteInfoDto requestDto);
    
    /**
     * 创建分享链接地址
     */
    CommonResult<SharepageLinkVo> createSharepageLink(SharepageLinkDto requestDto);
}
