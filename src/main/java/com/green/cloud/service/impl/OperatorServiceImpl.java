package com.green.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Operator;
import com.green.cloud.entity.OperatorGroup;
import com.green.cloud.mapper.OperatorMapper;
import com.green.cloud.service.IOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;

@Service
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, Operator> implements IOperatorService {

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public String getGroupNameById(Integer id) {
        return operatorMapper.getGroupNameById(id);
    }

    @Override
    public Result listOperatorGroup() {
        List<OperatorGroup> operatorGroups = operatorMapper.listOperatorGroup();
        return Result.ok(operatorGroups, Long.valueOf(operatorGroups.size()));
    }

    @Override
    public Result addOperator(Operator operator) {

        // 新增加的算子默认未审核
        operator.setStatus("未审核");
        boolean isSaved = save(operator);
        if (!isSaved){
            return Result.fail("添加算子失败");
        }
        return Result.ok("添加算子成功");

    }




    @Override
    public Result getByUUID(String uuid) {

        QueryWrapper<Operator> operatorQueryWrapper = new QueryWrapper<>();
        operatorQueryWrapper.eq("uuid", uuid);
        long count = count(operatorQueryWrapper);
        if (count > 0L){
            return Result.fail("算子已存在");
        }
        return Result.ok();
    }

    @Override
    public Result deleteOperatorById(Integer id) {

        boolean isRemoved = removeById(id);
        if (!isRemoved){
            return Result.fail("算子删除失败");
        }
        return Result.ok("算子删除成功");
    }
}
