package com.green.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.green.cloud.entity.ProcedureIndex;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface ProcedureIndexMapper extends BaseMapper<ProcedureIndex> {


    @Delete("DELETE FROM procedure_index WHERE procedurename = #{procedureName}")
    int DeleteByprocedurename(String procedureName);

    int updateByPrimaryKeySelective(ProcedureIndex record);

}