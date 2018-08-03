package com.msj.blog.entity.domain.login;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_role")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = -6647325354310157513L;

    private String name;
}
