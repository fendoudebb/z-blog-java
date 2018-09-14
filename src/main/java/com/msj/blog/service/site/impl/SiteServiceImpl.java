package com.msj.blog.service.site.impl;

import com.msj.blog.entity.domain.site.Site;
import com.msj.blog.repository.site.SiteRepository;
import com.msj.blog.service.category.PrimaryCategoryService;
import com.msj.blog.service.site.SiteService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * zbj: created on 2018/4/14 11:12.
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteRepository siteRepository;

    @Override
    public Map<String, List<Site>> findAll() {
        List<Site> sites = siteRepository.findAll(Sort.by(Sort.Order.asc("secondaryCategory.id"), Sort.Order.asc("sort")));
        Map<String, List<Site>> map = new LinkedHashMap<>();
        for (Site site : sites) {
            String name = site.getSecondaryCategory().getName();
            List<Site> classifySite = map.computeIfAbsent(name, k -> new ArrayList<>());
            classifySite.add(site);
        }
        return map;
    }
}
