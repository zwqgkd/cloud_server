package com.green.cloud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@TableName("tb_role")
@Data
public class Role {
    private Integer id;

    private String role;

    private String createUser;

    @TableField(exist = false)
    private List<Integer> permissionIds;


    @TableField(exist = false)
    private String formatCreateTime;

    @TableField(exist = false)
    private String formatUpdateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}