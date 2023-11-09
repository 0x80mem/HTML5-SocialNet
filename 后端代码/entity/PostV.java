package com.html.nds.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PostV {
    Integer id;
    String type;
    Integer author;
    ContentDTO content;
    List<Integer> parPost;
    List<Integer> chiPost;
    List<Integer> blackList = new ArrayList<>();
    List<Integer> whiteList = new ArrayList<>();
    List<Integer> grantGroup = new ArrayList<>();
    List<Integer> editGroup = new ArrayList<>();
    String baseRight = "banned";



    public PostV(NodeDTO nodeDTO) {

        id = nodeDTO.getId();
        type = nodeDTO.getType();
        author = nodeDTO.getAuthor();
        content = nodeDTO.getContent();
        parPost = nodeDTO.getParNode();
        chiPost = nodeDTO.getChiNodes();

    }

    public PostV(Integer id, String type, Integer author,ContentDTO content, List<Integer> parNode, List<Integer> chiPost) {
        this.id = id;
        this.type = type;
        this.author = author;
        this.content = content;
        this.parPost = parNode;
        this.chiPost = chiPost;
    }
}