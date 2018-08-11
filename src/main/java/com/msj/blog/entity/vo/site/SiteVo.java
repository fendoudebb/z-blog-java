package com.msj.blog.entity.vo.site;

import com.msj.blog.entity.domain.site.Site;
import lombok.Data;

import java.io.Serializable;

/**
 * zbj: created on 2018/4/14 10:57.
 */
@Data
public class SiteVo implements Serializable{

    private static final long serialVersionUID = -5941903800373490565L;
    private String name;
    private String url;

    public SiteVo() {

    }

    public SiteVo(Site site) {
        this.name = site.getName();
        this.url = site.getUrl();
    }

}
