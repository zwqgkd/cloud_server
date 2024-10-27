package com.green.cloud.entity;

import lombok.Data;

import java.util.Date;
@Data
public class License {
    private Integer id;

    private String licenseName;

    private String procedureName;

    private String description;

    private String licenseKey;

    private Date createTime;

    private Date exp;


}