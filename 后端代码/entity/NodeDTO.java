package com.html.nds.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class NodeDTO {

    private Integer id;

    private String type;

    private Integer author;

    private LocalDateTime time;

    private List<Integer> parNode = new ArrayList<>();

    private List<Integer> chiNodes = new ArrayList<>();

    private ContentDTO content;

    public void setParNode(Integer par){
        this.parNode.add(par);
    }

    public NodeDTO(PostV postV){
        id = postV.getId();
        type = postV.getType();
        author = postV.getAuthor();
        parNode = postV.getParPost();
        chiNodes = postV.getChiPost();
        content = postV.getContent();
    }
    public NodeDTO(Node node){
        id = node.getId();
        type = node.getType();
        author = node.getAuthor();
    }

}
