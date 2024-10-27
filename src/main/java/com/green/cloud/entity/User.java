package com.green.cloud.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


@TableName("tb_user")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String company;

    private String department;

    private String username;

    private String password;

    private Integer roleId;

    /**
     * 仅用于前端显示
     */
    @TableField(exist = false)
    private String role;

    @TableField(exist = false)
    private String formatCreateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String formatUpdateTime;


}