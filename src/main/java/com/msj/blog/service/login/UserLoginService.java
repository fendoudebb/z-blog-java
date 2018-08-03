package com.msj.blog.service.login;

import com.msj.blog.entity.domain.login.SysUser;
import com.msj.blog.repository.login.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserLoginService implements UserDetailsService {

    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("用户名: %s不存在", username)));
        log.info("request param: {} ,and username is {} and password is {}", username, user.getUsername(), user.getPassword());
        return user;
    }
}
