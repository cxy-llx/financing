package com.wulingqi.lightning.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qiniu.util.Auth;
import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.vo.QiniuVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 七牛云对象存储相关接口
 */
@RestController
@Api(tags = "QiniuController", description = "七牛存储管理")
@RequestMapping("/api/qiniu")
public class QiniuController {
	
	@Value("${qiniu.accessKey}")
	private String QINIU_ACCESS_KEY;
	
	@Value("${qiniu.secrectKey}")
	private String QINIU_SECRECT_KEY;
	
	@Value("${qiniu.bucket}")
	private String QINIU_BUCKET;
	
	@Value("${qiniu.host}")
	private String QINIU_HOST;
	
	@Value("${qiniu.zone}")
	private String QINIU_ZONE;
	
	@Value("${qiniu.cdnUrl}")
	private String QINIU_CDN_URL;

	@ApiOperation(value = "七牛存储上传token生成")
    @PostMapping
    public CommonResult<QiniuVo> token() {
		Auth auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRECT_KEY);
		String token = auth.uploadToken(QINIU_BUCKET);
		
		QiniuVo qiniuVo = new QiniuVo();
		qiniuVo.setToken(token);
		qiniuVo.setHost(QINIU_HOST);
		qiniuVo.setZone(QINIU_ZONE);
		qiniuVo.setCdnUrl(QINIU_CDN_URL);
		
		return CommonResult.success(qiniuVo);
    }
	
}
