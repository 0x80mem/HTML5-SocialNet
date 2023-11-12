package com.html.nds.service;

import com.html.nds.entity.ContentDTO;
import com.html.nds.entity.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import com.html.nds.entity.PostV;


public interface INodeService extends IService<Node> {
    Integer generateId();
    Integer createNode(Integer parNode,String type,Integer author,boolean backEdge);
    Integer createNode(Integer parNode,String type,ContentDTO contentDTO,Integer author,boolean backEdge);
    boolean removeNode(Integer parNode,Integer node);
    boolean updateContent(Integer id, ContentDTO content);
    ContentDTO getContent(Integer id);

    PostV getNode(Integer id);
}
