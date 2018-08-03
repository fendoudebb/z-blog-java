package com.msj.blog.repository.article;

import com.msj.blog.entity.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, PagingAndSortingRepository<Article, Long> {

    Page<Article> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    @Query(value = "select * from article where title = '关于我们' and audit_status = 4",nativeQuery = true)
    Article findAboutUs();
}
