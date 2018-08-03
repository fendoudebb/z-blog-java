package com.msj.blog.entity.domain.site;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * zbj: created on 2018/4/1 11:46.
 */
@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "site_detail",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "url"})})
public class SiteDetail extends BaseEntity {

    private static final long serialVersionUID = -7660383773690507443L;
    private Integer sort;//网址排序
    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private SiteModule siteModule;

}
