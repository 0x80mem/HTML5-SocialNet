package com.jlusw.html.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;

@Data
@TableName("user")
public class User {
    @TableId(value = "id",type=IdType.AUTO)
    private int id;
    private String name;
    private String password;
}
