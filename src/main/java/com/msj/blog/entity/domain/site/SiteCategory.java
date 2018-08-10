package com.msj.blog.entity.domain.site;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * zbj: created on 2018/4/1 11:54.
 */
@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "site_category",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SiteCategory extends BaseEntity {

    private static final long serialVersionUID = 4072846512022876087L;
    private Integer sort;//分类排序
    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name="site_category_module",joinColumns={@JoinColumn(name="category_id")},inverseJoinColumns={@JoinColumn(name="module_id")})
    @OrderBy(value="sort asc")
    private Set<SiteModule> siteModules;

}
