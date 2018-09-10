package com.msj.blog.service.cache;

/**
 * zbj: created on 2018/8/28 19:59.
 */
public interface CacheService {

    String getArticle(Long id);

    void setArticle(Long id, String article);

    String getArticlePage(String secondaryCategoryName, Integer page, Integer count);

    void setArticlePage(String secondaryCategoryName, Integer page, Integer count, String articlePage);

    String getSecondaryCategoryNames(String primaryCategoryName);

    void setSecondaryCategoryNames(String primaryCategoryName, String secondaryCategoryNames);

}
