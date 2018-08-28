package com.msj.blog.config.security;

import com.msj.blog.service.login.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.annotation.Resource;

/**
 * bcrypt - BCryptPasswordEncoder (Also used for encoding)
 * ldap - LdapShaPasswordEncoder
 * MD4 - Md4PasswordEncoder
 * MD5 - new MessageDigestPasswordEncoder("MD5")
 * noop - NoOpPasswordEncoder
 * pbkdf2 - Pbkdf2PasswordEncoder
 * scrypt - SCryptPasswordEncoder
 * SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
 * SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
 * sha256 - StandardPasswordEncoder
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// 控制权限注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //加密"0"
        String encode = bCryptPasswordEncoder.encode("MR`&D=+UP*?9");
        System.out.println(encode);
//        boolean matches = bCryptPasswordEncoder.matches("111111", "$2a$10$zsIgQVyHTUU9pE.gplXxduCNyZPpLiuOm1RDSZyShOYX3TMj9X8Ay");
        boolean matches = bCryptPasswordEncoder.matches("MR`&D=+UP*?9", "$2a$10$4ZB0aMz5zVZ9TDF14OMmNup4WXEXTuKIuDL0o2zwT0p2cEYWxfoj6");
        System.out.println(matches);
        //结果：$2a$10$TYuFYHUe/EoyMnNeyHZDG.93Py6C/PmKlTjg8Lvpuf5eRDxeJA1v2
    }

    @Resource
    private UserLoginService userLoginService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private LogoutHandler logoutHandler;
    @Resource
    private AuthEntryPoint authEntryPoint;
    @Resource
    private PermissionDenyHandler permissionDenyHandler;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoginService)/*.passwordEncoder(new BCryptPasswordEncoder())*/;//spring security4
        auth.userDetailsService(userLoginService);//spring security5 已过时
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        /*web.ignoring()
                .antMatchers("/js/**")
                .antMatchers("/css/**");*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()

                .authorizeRequests()
//                .antMatchers("/admin/**").authenticated()
                .antMatchers("/aaaaaa/**").authenticated()
                //任何访问都必须授权
                //.anyRequest().authenticated()

                .and()
                    //下面两行作用是启用默认登录页
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .permitAll()
                .and()
                    .rememberMe()  //启用“记住我"功能
                    .tokenValiditySeconds(2419200)
                    .rememberMeParameter("remember-me")
                    .key("msg-admin-key")
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutHandler)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authEntryPoint)
                    .accessDeniedHandler(permissionDenyHandler)
                .and()
//                    .headers().frameOptions().disable() // 解决IFrame x-frame-options deny
                    .headers().frameOptions().sameOrigin() // 只能在本应用的iFrame加载,其他的应用不会加载
                /*.and()
                    .exceptionHandling()
                    .accessDeniedPage("/error/403")//已被ControllerAdvice拦截,不会走这个方法*/
                /*.and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/login?expire")
                    .sessionRegistry(getSessionRegistry())*/;
        // @formatter:on

        /*http
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(new GoAccessDeniedHandler())
                .authenticationEntryPoint(new GoAuthenticationEntryPoint())
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/csrf").permitAll()
                    .antMatchers("/hello").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login").permitAll()
                    .successHandler(new GoAuthenticationSuccessHandler())
                    .failureHandler(new GoAuthenticationFailureHandler())
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(new GoLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                *//*.and()
                    .requiresChannel()
                    .antMatchers("/pomer").requiresSecure()
                    .anyRequest().requiresInsecure()*//*
                .and()
                    .rememberMe()
                    .tokenValiditySeconds(1800)
                    .key("token_key");*/
    }

}