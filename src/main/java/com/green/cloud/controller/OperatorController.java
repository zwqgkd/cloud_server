package com.green.cloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.green.cloud.common.Result;
import com.green.cloud.entity.Operator;
import com.green.cloud.service.IOperatorService;
import com.green.cloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private IOperatorService operatorService;

    @Autowired
    private IUserService userService;


    @GetMapping("/page")
    public Result page(@RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                       @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {

        Page<Operator> pageInfo = new Page<>(currentPage, pageSize);
        Page<Operator> page = operatorService.page(pageInfo);

        long total = page.getTotal();
        List<Operator> records = page.getRecords();

        List<Operator> operators = records.stream().peek(operator -> {

            // need optimize
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Integer groupId = operator.getGroupId();
                    String groupNameById = operatorService.getGroupNameById(groupId);

                    Integer ownerId = operator.getOwnerId();
                    String name = userService.getById(ownerId).getName();

                    operator.setOwnerName(name);
                    operator.setGroupName(groupNameById);
                    operator.setFormatUpdateTime(operator.getUpdateTime().format(dateTimeFormatter));
                }

        ).toList();


        return Result.ok(operators, total);
    }


    @GetMapping("/listGroupName")
    public Result listOperatorGroupName(){
        return operatorService.listOperatorGroup();
    }

    @PostMapping("/add")
    public Result addOperator(@RequestBody Operator operator){
        return operatorService.addOperator(operator);
    }

    @GetMapping("/isExists/{uuid}")
    public Result isExistsByUUID(@PathVariable("uuid") String uuid){

        return operatorService.getByUUID(uuid);
    }

    @GetMapping("/delete/{id}")
    public Result deleteOperatorById(@PathVariable("id") Integer id){
        return operatorService.deleteOperatorById(id);
    }

}
