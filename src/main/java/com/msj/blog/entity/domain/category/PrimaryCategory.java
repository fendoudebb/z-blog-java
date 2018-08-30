package com.msj.blog.entity.domain.category;

import com.msj.blog.entity.FieldLength;
import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * 文章所属一级分类
 * 如IT,常识等
 */
@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "primary_category",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class PrimaryCategory extends BaseEntity {

    private static final long serialVersionUID = 2205376374916451004L;
    private Integer sort;//模块排序
    @Column(name = "name", length = FieldLength.NORMAL, nullable = false)
    private String name;//模块名字

    @OneToMany(mappedBy = "primaryCategory")
    @OrderBy(value = "sort asc")
    private Set<SecondaryCategory> secondaryCategories;

}

