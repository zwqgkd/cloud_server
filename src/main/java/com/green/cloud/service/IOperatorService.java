package com.green.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Operator;

public interface IOperatorService extends IService<Operator> {

    String getGroupNameById(Integer id);

    Result listOperatorGroup();

    Result addOperator(Operator operator);

    Result getByUUID(String uuid);

    Result deleteOperatorById(Integer id);


}
