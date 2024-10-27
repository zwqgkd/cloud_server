package com.green.cloud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAuth implements Serializable {
    private String username;
    private Integer role;
    private List<String> permissions;

    // 省略 getter 和 setter 方法

}

