package com.msj.blog.entity.domain.enu;

/**
 * zbj: create on 2018/08/07 11:12
 */
public enum ArticleProperty {
    /**
     * type = 0 草稿
     * type = 1 公开
     * type = 2 个人
     * ...
     */
    DRAFT(0, "草稿"),
    PUBLIC(1, "公开"),
    PRIVATE(2, "个人");

    private Integer type;

    private String description;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ArticleProperty(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}
