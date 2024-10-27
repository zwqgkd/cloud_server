package com.green.cloud.common;

import lombok.Data;

import java.util.List;


@Data
public class Model {
    private String modelName;
    private String helpMsg;
    private String dllPath;
    private String modelID;
    private List<InputParameter> inPara;
    private List<OutputParameter> outPara;

    // 构造函数、getter和setter方法省略


}
