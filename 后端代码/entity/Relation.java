package com.html.nds.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("relation")
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer parent;

    private Integer child;

    private LocalDateTime time;

    public Relation(Integer parent, Integer child) {
        this.parent = parent;
        this.child = child;
        this.time = LocalDateTime.now();
    }
}
