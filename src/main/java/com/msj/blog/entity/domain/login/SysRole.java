package com.msj.blog.entity.domain.login;

import com.msj.blog.entity.FieldLength;
import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = -6647325354310157513L;

    @Column(name = "name", length = FieldLength.NORMAL, nullable = false)
    private String name;
}
