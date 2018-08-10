package com.msj.blog.repository.article;

import com.msj.blog.entity.domain.article.Article;
import com.msj.blog.entity.domain.enu.ArticleProperty;
import com.msj.blog.entity.domain.enu.AuditStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, PagingAndSortingRepository<Article, Long> {

    Optional<Article> findByIdAndAuditStatusEqualsAndArticlePropertyEquals(Long id, AuditStatus auditStatus, ArticleProperty articleProperty);

    Page<Article> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    Page<Article> findAllByAuditStatusEqualsAndArticlePropertyEqualsOrderByUpdateTimeDesc(AuditStatus auditStatus, ArticleProperty articleProperty, Pageable pageable);

    Stream<Article> findAllByAuditStatusEqualsAndArticlePropertyEquals(AuditStatus auditStatus, ArticleProperty articleProperty);

    @Query(value = "select * from article where title = '关于我们' and audit_status = 4",nativeQuery = true)
    Article findAboutUs();
}
