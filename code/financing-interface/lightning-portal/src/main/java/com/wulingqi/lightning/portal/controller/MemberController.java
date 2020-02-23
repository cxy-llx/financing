package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.dto.AuthCodeDto;
import com.wulingqi.lightning.portal.dto.EditPasswordDto;
import com.wulingqi.lightning.portal.dto.ForgetPasswordDto;
import com.wulingqi.lightning.portal.dto.LoginDto;
import com.wulingqi.lightning.portal.dto.RegisterDto;
import com.wulingqi.lightning.portal.service.MemberService;
import com.wulingqi.lightning.portal.vo.LoginVo;
import com.wulingqi.lightning.portal.vo.MemberInfoVo;

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

}
