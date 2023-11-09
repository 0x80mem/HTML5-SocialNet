package com.html.nds.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("node")
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private Integer author;

    private String type;

    private LocalDateTime time;

    public Node(Integer id,Integer author, String type) {

        this.id = id;
        this.author=author;
        this.type = type;
        this.time = LocalDateTime.now();
    }

}
