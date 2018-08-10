package com.msj.blog.entity.domain.article;

import com.msj.blog.entity.domain.BaseEntity;
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

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "author", columnDefinition = "varchar(20) not null default \"msj\"")
    private String author;

    @Column(columnDefinition = "bit(1) default 1")
    private boolean original;//是否原创

    private String originalLink;//原文链接

    @Column(name = "keywords", length = 30)
    private String keywords;

    @Column(name = "description", length = 80)
    private String description;

    @Lob
    private String content;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ArticleCategory articleCategory;

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
