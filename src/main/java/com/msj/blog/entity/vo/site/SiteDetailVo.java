package com.msj.blog.entity.vo.site;

import com.msj.blog.entity.domain.site.SiteDetail;
import lombok.Data;

import java.io.Serializable;

/**
 * zbj: created on 2018/4/14 10:57.
 */
@Data
public class SiteDetailVo implements Serializable{

    private static final long serialVersionUID = -5941903800373490565L;
    private String name;
    private String url;

    public SiteDetailVo() {

    }

    public SiteDetailVo(SiteDetail siteDetail) {
        this.name = siteDetail.getName();
        this.url = siteDetail.getUrl();
    }

}
