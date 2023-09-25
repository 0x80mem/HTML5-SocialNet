package com.jlusw.html.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long postId;
    private Long authorId;
    private String authorName;
    private String content;
    private LocalDateTime time;
    private Integer likes;
    private Integer share;
    private Integer collect;
}
