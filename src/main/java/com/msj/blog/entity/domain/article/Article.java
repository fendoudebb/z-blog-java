package com.msj.blog.entity.domain.article;

import com.msj.blog.entity.FieldLength;
import com.msj.blog.entity.domain.BaseEntity;
import com.msj.blog.entity.domain.category.SecondaryCategory;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import com.msj.blog.entity.domain.enu.MarkupLanguage;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Table(name = "article", indexes = {@Index(columnList = "title"), @Index(columnList = "author")})
public class Article extends BaseEntity {
    private static final long serialVersionUID = 956501929140358804L;

    @Column(name = "title", length = FieldLength.ARTICLE_TITLE, nullable = false)
    private String title;

    @Column(name = "author", columnDefinition = "varchar(" + FieldLength.ARTICLE_AUTHOR + ") not null default \"msj\"")
    private String author;

    @Column(columnDefinition = "bit(1) default 1")
    private boolean original;//是否原创

    @Column(name = "original_link", length = FieldLength.URL_LINK)
    private String originalLink;//原文链接

    @Column(name = "keywords", length = FieldLength.ARTICLE_KEYWORDS)
    private String keywords;

    @Column(name = "description", length = FieldLength.ARTICLE_DESCRIPTION)
    private String description;

    @Lob
    private String content;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "secondary_category_id")
    private SecondaryCategory secondaryCategory;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int(11) not null default 0")
    private MarkupLanguage markupLanguage;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int(11) not null default 0")
    private AuditStatus auditStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "int(11) not null default 0")
    private ArticleProperty articleProperty;

}
