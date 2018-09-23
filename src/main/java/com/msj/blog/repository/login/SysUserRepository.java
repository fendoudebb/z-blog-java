package com.msj.blog.repository.login;

import com.msj.blog.entity.domain.login.SysUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends PagingAndSortingRepository<SysUser, Long> {

    SysUser findByUsername(String username);

}
