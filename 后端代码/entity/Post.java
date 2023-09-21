package com.jlusw.html.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("post")
public class Post {

    @TableId(value = "id",type= IdType.AUTO)
    //帖子id
    private long id;

    //发帖人id
    private long userId;

    //帖子内容
    private String content;

    //帖子发布时间
    private LocalDateTime time;

    //帖子获赞数
    private int likes;

    //帖子收藏数
    private int collect;

    //帖子转发数
    private int share;

    //帖子状态 0 未删除 1 删除
    private int state;
}
