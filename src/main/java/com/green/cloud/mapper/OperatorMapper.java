package com.green.cloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.Operator;
import com.green.cloud.entity.OperatorGroup;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OperatorMapper extends BaseMapper<Operator> {

    @Select("select name from tb_operator_group where id = #{id}")
    String getGroupNameById(Integer id);


    @Select("select * from tb_operator_group")
    List<OperatorGroup> listOperatorGroup();

}
