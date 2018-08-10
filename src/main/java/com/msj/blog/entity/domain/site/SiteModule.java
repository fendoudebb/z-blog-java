package com.msj.blog.entity.domain.site;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "site_module",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SiteModule extends BaseEntity {

    private static final long serialVersionUID = 3580741047829758549L;
    private Integer sort;//模块排序
    @Column(name = "name", length = 16, nullable = false)
    private String name;//模块名字
    @Column(name = "alias", length = 16, nullable = false)
    private String alias;//别名

    @ManyToMany(mappedBy = "siteModules")
    private Set<SiteCategory> siteCategories;

    @OneToMany(mappedBy = "siteModule")
    @OrderBy(value="sort asc")
    private Set<SiteDetail> siteDetails;


}
