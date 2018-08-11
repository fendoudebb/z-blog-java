package com.msj.blog.entity.domain.site;

import com.msj.blog.entity.FieldLength;
import com.msj.blog.entity.domain.BaseEntity;
import com.msj.blog.entity.domain.category.SecondaryCategory;
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
@Table(name = "site",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "url"})})
public class Site extends BaseEntity {

    private static final long serialVersionUID = -7660383773690507443L;
    private Integer sort;//网址排序
    @Column(name = "name", length = FieldLength.NORMAL, nullable = false)
    private String name;
    @Column(name = "url", length = FieldLength.URL_LINK, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "secondary_category_id")
    private SecondaryCategory secondaryCategory;

}
