package com.msj.blog.config.cache;

/**
 * zbj: created on 2018/8/28 19:56.
 */
public class CacheKey {

    public static final String KEY_PREFIX_ARTICLE_VO = "article-vo:";
    public static final String KEY_PREFIX_ARTICLE_PAGE = "article-page:";
    public static final String KEY_PREFIX_ARTICLE_ABOUT_US = KEY_PREFIX_ARTICLE_VO.concat("about_us");
    public static final String KEY_PREFIX_ARTICLE_DISCLAIMER = KEY_PREFIX_ARTICLE_VO.concat("disclaimer");

}
