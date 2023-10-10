package com.html.nds.entity;

import lombok.Data;

@Data
public class PostDTO {
    private NodeDTO node;

    private Integer author;

    public PostDTO(NodeDTO node, Integer author) {
        this.node = node;
        this.author = author;
    }
}
