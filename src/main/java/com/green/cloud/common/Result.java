package com.green.cloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean success;
    private String msg;
    // 返回的数据
    private Object data;
    // 若返回列表数据，表示列表大小
    private Long size;

    public static Result ok() {
        return new Result(true, null, null, null);
    }


    public static Result ok(String msg) {
        return new Result(true, msg, null, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(true, msg, data, null);
    }


    public static Result ok(List<?> data, Long size) {
        return new Result(true, null, data, size);
    }

    public static Result fail(String msg) {
        return new Result(false, msg, null, null);
    }
}