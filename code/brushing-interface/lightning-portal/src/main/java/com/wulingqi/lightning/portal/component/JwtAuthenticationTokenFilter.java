package com.wulingqi.lightning.portal.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wulingqi.lightning.api.CommonResult;
import com.wulingqi.lightning.portal.domain.MemberDetails;
import com.wulingqi.lightning.portal.service.CommonService;
import com.wulingqi.lightning.portal.util.JwtTokenUtil;
import com.wulingqi.lightning.utils.LightningConstant;

import cn.hutool.json.JSONUtil;

/**
 * JWT登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
	private CommonService commonService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
    	
    	boolean systemMaintenanceSwitch = Boolean.valueOf(commonService.getDictionaryValue(LightningConstant.SYSTEM_MAINTENANCE_SWITCH));
		
		//系统维护开关(false表示没有维护,true表示系统维护中)
		if(systemMaintenanceSwitch) {
			response.setStatus(HttpStatus.OK.value());
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json");
	        response.getWriter().println(JSONUtil.parse(CommonResult.failed("系统维护中，请稍后再试")));
	        response.getWriter().flush();
	        return;
		}
    	
    	String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            	
            	MemberDetails userDetails = (MemberDetails) this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                
            }
        }
        
        //允许跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
        chain.doFilter(request, response);
        
    }
}
