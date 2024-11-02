package com.green.cloud.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_operator_group")
public class OperatorGroup {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

}
