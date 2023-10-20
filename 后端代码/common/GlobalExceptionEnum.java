package com.html.nds.common;

import lombok.Getter;


/**
 * 枚举类封装错误码信息：
 */
@Getter
public enum GlobalExceptionEnum {

    SUCCESS(1, "操作成功!"),
    ERROR(0,"系统错误");
    // 2. 枚举实例
    private final Integer code; // 错误码
    private final String message; // 错误码对应的错误信息提示语

    // 3. 字段的构造方法


    GlobalExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
