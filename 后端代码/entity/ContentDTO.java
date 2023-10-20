package com.html.nds.entity;

import lombok.Data;

@Data
public class ContentDTO {

    private String title;

    private String content;

    public ContentDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ContentDTO() {

    }
}
