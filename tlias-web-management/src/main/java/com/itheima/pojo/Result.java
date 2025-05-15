package com.itheima.pojo;

import lombok.Data;

@Data
public class Result {
    private  Integer code;
    private  String msg;
    private  Object data;
    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        result.data = object;
        return result;
    }
    public static Result error(String msg) {
        Result result = new Result();
        result.code = 0;
        result.msg = msg;
        return result;
    }
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }
}
