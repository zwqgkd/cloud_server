package com.green.cloud.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_permission")
public class Permission {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String groupName;

}
