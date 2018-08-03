package com.msj.blog.repository.article;

import com.msj.blog.entity.domain.article.ArticleModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * zbj: created on 2018/6/1 21:38.
 */
@Repository
public interface ArticleModuleRepository extends JpaRepository<ArticleModule, Long>, PagingAndSortingRepository<ArticleModule, Long> {

}
