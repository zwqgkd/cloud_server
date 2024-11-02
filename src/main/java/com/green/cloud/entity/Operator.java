package com.green.cloud.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_operator")
public class Operator {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String uuid;

    private String dllPath;

    private String jsonPath;

    private String softwareVersion;

    private String description;

    private Integer groupId;

    @TableField(exist = false)
    private String groupName;

    private Integer ownerId;

    @TableField(exist = false)
    private String ownerName;

    private String status;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String formatUpdateTime;

}
