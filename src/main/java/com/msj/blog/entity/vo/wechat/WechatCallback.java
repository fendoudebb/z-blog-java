package com.msj.blog.entity.vo.wechat;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * zbj: created on 2018/6/11 21:20.
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WechatCallback implements Serializable{
    private static final long serialVersionUID = -83377628655185947L;
    @XmlElement(name = "ToUserName")
    private String ToUserName;
    @XmlElement(name = "FromUserName")
    private String FromUserName;
    @XmlElement(name = "CreateTime")
    private String CreateTime;
    @XmlElement(name ="MsgType")
    private String MsgType;
    @XmlElement(name = "Event")
    private String Event;
    @XmlElement(name = "MsgID")
    private String MsgID;
    @XmlElement(name = "Status")
    private String Status;
}
