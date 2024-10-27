package com.green.cloud.entity;

import lombok.Data;

@Data
public class File {
    private Integer id;

    private String fileName;

    private Integer fatherId;

    private Integer isDir;

    private Integer localRemote;

}