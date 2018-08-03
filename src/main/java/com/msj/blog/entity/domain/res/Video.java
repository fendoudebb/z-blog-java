package com.msj.blog.entity.domain.res;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "video")
public class Video extends BaseEntity {

    private static final long serialVersionUID = -4241988166729685104L;
    private String name;
    private String star;//主演
    private String url;//资源存放路径
    private String pwd;
    private String size;
}
