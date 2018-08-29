package com.msj.blog.service.cache;

/**
 * zbj: created on 2018/8/28 19:59.
 */
public interface CacheService {

    String getArticle(Long id);

    void setArticle(Long id, String article);

    String getArticlePage(Integer page, Integer count);

    void setArticlePage(Integer page, Integer count, String articlePage);

    String getAboutUsArticle();

    void setAboutUsArticle(String aboutUsArticle);

    String getDisclaimerArticle();

    void setDisclaimerArticle(String disclaimerArticle);

}
