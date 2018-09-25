package com.msj.blog.entity.domain.category;

import com.msj.blog.entity.FieldLength;
import com.msj.blog.entity.domain.BaseEntity;
import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.site.Site;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * 文章所属二级分类
 * 如Java,Android
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "secondary_category")
public class SecondaryCategory extends BaseEntity {
    private static final long serialVersionUID = -9123364858524160112L;

    public SecondaryCategory(String name) {
        this.name = name;
    }

    private Integer sort;//分类排序
    @Column(name = "name", length = FieldLength.NORMAL, nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "primary_category_id")
    private PrimaryCategory primaryCategory;

    @OneToMany(mappedBy = "secondaryCategory")
    private Set<Article> articles;

    @OneToMany(mappedBy = "secondaryCategory")
    private Set<Site> sites;

}
