package com.msj.blog.entity.vo.site;

import com.msj.blog.entity.domain.site.SiteCategory;
import com.msj.blog.entity.domain.site.SiteModule;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class SiteCategoryVo implements Serializable{

    private static final long serialVersionUID = 2386572075401320957L;
    private Integer sort;//分类排序
    private String name;

    private List<SiteModuleVo> modules;

    public SiteCategoryVo() {}

    public SiteCategoryVo(SiteCategory siteCategory) {
        this.sort = siteCategory.getSort();
        this.name = siteCategory.getName();
        modules = new ArrayList<>();
        Set<SiteModule> siteModules = siteCategory.getSiteModules();
        for (SiteModule siteModule : siteModules) {
            modules.add(new SiteModuleVo(siteModule));
        }
    }

}
