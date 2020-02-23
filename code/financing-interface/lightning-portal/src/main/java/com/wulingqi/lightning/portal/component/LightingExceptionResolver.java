package com.wulingqi.lightning.portal.component;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.utils.LightningConstant;

import cn.hutool.json.JSONUtil;

/**
 * 统一异常处理类 
 */
public class LightingExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object, Exception exception) {
		
		ServletOutputStream outputStream = null;
		try {
		    //有问题的部份
			//PrintWriter out = null;
			//out = response.getWriter();
			// String json = new ObjectMapper().writeValueAsString(map);
			//out.write(json);
			//out.flush();
			//out.close();

			outputStream = response.getOutputStream();
			outputStream.write(JSONUtil.parse(CommonResult.failed(LightningConstant.SERVER_ERROR)).toString().getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
        return null;
	}

}
