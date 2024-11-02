package com.green.cloud.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class UserAuth implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;


    private String token;

    private String email;
    private Integer id;

    private Integer roleId;
    private String role;
    private List<String> permissions;

    // 省略 getter 和 setter 方法

}

