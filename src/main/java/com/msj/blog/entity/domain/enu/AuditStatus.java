package com.msj.blog.entity.domain.enu;

public enum AuditStatus {
    /**
     * type = 0 待审核
     * type = 1 上线
     * type = 2 审核拒绝
     * type = 3 下线
     * type = 4 特殊条件查询
     */
    pending(0, "待审核"),
    online(1, "上线"),
    disapproved(2, "审核拒绝"),
    offline(3, "下线"),
    special(4, "特殊情况");

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

    AuditStatus(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public static AuditStatus getStatus(Integer type) {
        if (type == null) {
            return null;
        }
        AuditStatus[] res = AuditStatus.values();
        for (AuditStatus re : res) {
            if (type.equals(re.getType())) {
                return re;
            }
        }
        return null;
    }
}
