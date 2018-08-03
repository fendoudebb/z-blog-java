package com.msj.blog.entity.domain.res;

import com.msj.blog.entity.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "music")
public class Music extends BaseEntity {

    private static final long serialVersionUID = -5943673385516075769L;
    private String name;
    private String author;
    private String lyrics;//歌词
    private String playUrl;//外链： 网易云外链
    private String url;//资源存放路径
    private String pwd;
    private String size;
}
