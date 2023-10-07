package com.html.nds.common;

import lombok.Data;

/**
 * 自定义全局异常类GlobalException
 */
@Data
public class GlobalException extends RuntimeException{

    private Integer code;
    private String globalMessage;

    public GlobalException() {
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
        this.globalMessage = message;
    }

    // 建立直接传入枚举做参数可以直接创建对应的异常
    public GlobalException(GlobalExceptionEnum globalExceptionEnum) {
        super(globalExceptionEnum.getMessage());
        this.code = globalExceptionEnum.getCode();
        this.globalMessage = globalExceptionEnum.getMessage();
    }
}

