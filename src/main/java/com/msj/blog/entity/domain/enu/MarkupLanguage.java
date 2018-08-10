package com.msj.blog.entity.domain.enu;

public enum MarkupLanguage {
    /**
     * type = 0 markdown
     * type = 1 AsciiDoc
     * type = 2 rich
     */
    markdown(0, "Markdown"),
    ascii_doc(1, "AsciiDoc"),
    rich(2, "富文本");

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

    MarkupLanguage(Integer type, String description) {
        this.type = type;
        this.description = description;
    }
}
