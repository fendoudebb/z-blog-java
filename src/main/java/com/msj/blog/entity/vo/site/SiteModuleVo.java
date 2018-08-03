package com.msj.blog.entity.vo.site;

import com.msj.blog.entity.domain.site.SiteDetail;
import com.msj.blog.entity.domain.site.SiteModule;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * zbj: created on 2018/4/14 10:45.
 */
@Data
public class SiteModuleVo implements Serializable{
    private static final long serialVersionUID = 2161450033264452250L;
    private String name;

    private String alias;

    private List<SiteDetailVo> sites;

    private SiteModuleVo() {

    }

    public SiteModuleVo(SiteModule siteModule) {
        this.name = siteModule.getName();
        this.alias = siteModule.getAlias();
        sites = new ArrayList<>();
        Set<SiteDetail> siteDetails = siteModule.getSiteDetails();
        for (SiteDetail siteDetail : siteDetails) {
            sites.add(new SiteDetailVo(siteDetail));
        }
    }
}
