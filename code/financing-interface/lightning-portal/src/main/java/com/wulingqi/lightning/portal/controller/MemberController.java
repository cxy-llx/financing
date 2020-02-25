package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.AuthCodeDto;
import com.wulingqi.lightning.portal.dto.CompleteInfoDto;
import com.wulingqi.lightning.portal.dto.EditPasswordDto;
import com.wulingqi.lightning.portal.dto.ForgetPasswordDto;
import com.wulingqi.lightning.portal.dto.SharepageLinkDto;
import com.wulingqi.lightning.portal.dto.LoginDto;
import com.wulingqi.lightning.portal.dto.RegisterDto;
import com.wulingqi.lightning.portal.dto.TeamInfoDto;
import com.wulingqi.lightning.portal.dto.UpdateAvatarUrlDto;
import com.wulingqi.lightning.portal.dto.UpdateNicknameDto;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.vo.SharepageLinkVo;
import com.wulingqi.lightning.portal.vo.LoginVo;
import com.wulingqi.lightning.portal.vo.MemberInfoVo;
import com.wulingqi.lightning.portal.vo.TeamInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员管理Controller
 */
@RestController
@Api(tags = "MemberController", description = "会员管理")
@RequestMapping("/api/member")
public class MemberController {
	
    @Autowired
    private MemberService memberService;

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<LoginVo> register(@RequestBody RegisterDto requestDto) {
        return memberService.register(requestDto);
    }
    
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<LoginVo> login(@RequestBody LoginDto requestDto) {
        return memberService.login(requestDto);
    }
    
    @ApiOperation(value = "忘记密码")
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public CommonResult<String> forgetPassword(@RequestBody ForgetPasswordDto requestDto) {
        return memberService.forgetPassword(requestDto);
    }
    
    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public CommonResult<String> editPassword(@RequestBody EditPasswordDto requestDto) {
        return memberService.editPassword(requestDto);
    }
    
    @ApiOperation("修改会员昵称")
    @RequestMapping(value = "/updateNickname", method = RequestMethod.POST)
    public CommonResult<String> updateNickname(@RequestBody UpdateNicknameDto requestDto) {
        memberService.updateNickname(requestDto);
        return CommonResult.success(null, "修改成功");
    }
    
    @ApiOperation("修改会员头像")
    @RequestMapping(value = "/updateAvatarUrl", method = RequestMethod.POST)
    public CommonResult<String> updateAvatarUrl(@RequestBody UpdateAvatarUrlDto requestDto) {
    	memberService.updateAvatarUrl(requestDto);
    	return CommonResult.success(null, "修改成功");
    }
    
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.POST)
    public CommonResult<String> getAuthCode(@RequestBody AuthCodeDto requestDto) {
        return memberService.getAuthCode(requestDto);
    }

    @ApiOperation("获取会员个人信息")
    @RequestMapping(value = "/getMemberInfo", method = RequestMethod.POST)
    public CommonResult<MemberInfoVo> getMemberInfo() {
		MemberInfoVo result = memberService.getMemberInfo();
        return CommonResult.success(result);
    }
    
    @ApiOperation(value = "获取我的团队信息")
    @RequestMapping(value = "/getTeamInfo", method = RequestMethod.POST)
    public CommonResult<TeamInfoVo> getTeamInfo(@RequestBody TeamInfoDto requestDto) {
        return memberService.getTeamInfo(requestDto);
    }
    
    @ApiOperation(value = "完善资料信息")
    @RequestMapping(value = "/completeInfo", method = RequestMethod.POST)
    public CommonResult<String> completeInfo(@RequestBody CompleteInfoDto requestDto) {
        return memberService.completeInfo(requestDto);
    }
    
    @ApiOperation(value = "创建分享链接地址")
    @RequestMapping(value = "/createSharepageLink", method = RequestMethod.POST)
    public CommonResult<SharepageLinkVo> createSharepageLink(@RequestBody SharepageLinkDto requestDto) {
        return memberService.createSharepageLink(requestDto);
    }

}
