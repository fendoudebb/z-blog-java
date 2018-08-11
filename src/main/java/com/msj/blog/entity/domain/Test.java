package com.msj.blog.entity.domain;

import com.msj.blog.entity.domain.enu.AuditStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
@Table(name = "test",indexes = {@Index(columnList = "testName")})
public class Test extends BaseEntity {
    private static final long serialVersionUID = 507207113720884370L;

    @Column(columnDefinition = "int(11) unsigned")
    private Integer num;

    private String testName;

//    @Formula(value = "select count(*) from test")
//    private Integer count;

    @Enumerated(EnumType.ORDINAL)
    private AuditStatus auditStatus;

}
