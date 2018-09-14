package com.msj.blog.service.site;


import com.msj.blog.entity.domain.site.Site;

import java.util.List;
import java.util.Map;

/**
 * zbj: created on 2018/4/14 11:03.
 */
public interface SiteService {

    Map<String, List<Site>> findAll();
}
