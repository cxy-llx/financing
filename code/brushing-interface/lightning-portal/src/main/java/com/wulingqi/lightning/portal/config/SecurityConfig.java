package com.wulingqi.lightning.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.portal.component.JwtAuthenticationTokenFilter;
import com.wulingqi.lightning.portal.component.RestAuthenticationEntryPoint;
import com.wulingqi.lightning.portal.component.RestfulAccessDeniedHandler;
import com.wulingqi.lightning.portal.domain.MemberDetails;
import com.wulingqi.lightning.portal.service.MemberService;

/**
 * SpringSecurity的配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
    	httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
        .disable()
        .sessionManagement()// 基于token，所以不需要session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        )
        .permitAll()
        .antMatchers(
        		"/api/member/register", //注册
                "/api/member/login", //登录
                "/api/member/forgetPassword", //忘记密码
                "/api/member/getAuthCode", //获取验证码
                "/api/other/getAndroidUpgrade", //获取安卓版本更新
                "/api/other/getAppleUpgrade", //获取苹果版本更新
                "/api/other/getCommonInfo", //获取公共信息接口
                "/websocket/**"//首页接口
        )
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
        .permitAll()
        //.antMatchers("/**")//测试时全部运行访问
        //.permitAll()
        .anyRequest()// 除上面外的所有请求全部需要鉴权认证
        .authenticated();
    	
		// 禁用缓存
		httpSecurity.headers().cacheControl();
		// 添加JWT filter
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		httpSecurity.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint);
    	
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Member member = memberService.getByUsername(username);
                if(member!=null){
                    return new MemberDetails(member);
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }
    
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
