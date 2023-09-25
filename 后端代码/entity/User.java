package com.jlusw.html.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("user")
public class User {
    @TableId(value = "id",type=IdType.AUTO)
    private Long id;
    private String name;
    private String password;
    private LocalDateTime registerTime;
}
