package com.green.cloud.common;


import lombok.Data;

@Data
public class InputParameter {
    private String varName;
    private String varType;
    private String fromExpression;
    private String defineVarInputWay;
    private Object comboList;
    private String varExplanation;

    // 构造函数、getter和setter方法省略


}
