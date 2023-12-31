package com.html.nds.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map<String,Object> map = new HashMap<String,Object>(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.msg = "成功";
        r.data = object;
        r.code = 1;
        return r;
    }
    public static <T> R<T> success() {
        R<T> r = new R<T>();
        r.msg = "成功";
        r.data = null;
        r.code = 1;
        return r;
    }
    public static <T> R<T> error(Integer code ,String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = code;
        return r;
    }
    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 0;
        return r;
    }
    public static <T> R<T> error() {
        R<T> r = new R<T>();
        r.msg = "失败";
        r.code = 0;
        return r;
    }
    public void add(String key, Object value) {
        this.map.put(key, value);
    }
    public static R<String> result(boolean flag){
        if(flag)
            return R.success();
        return R.error();
    }

}

