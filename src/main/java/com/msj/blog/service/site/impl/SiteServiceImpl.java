package com.msj.blog.service.site.impl;

import com.msj.blog.repository.site.SiteRepository;
import com.msj.blog.service.site.SiteService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * zbj: created on 2018/4/14 11:12.
 */
@Service
@CacheConfig(cacheNames = "site")
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteRepository siteRepository;


}
