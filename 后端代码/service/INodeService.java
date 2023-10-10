package com.html.nds.service;

import com.html.nds.entity.ContentDTO;
import com.html.nds.entity.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import com.html.nds.entity.NodeDTO;


public interface INodeService extends IService<Node> {
    Integer generateId();
    Integer createNode(Integer parNode,String type);
    Integer createNode(Integer parNode,String type,ContentDTO contentDTO);
    boolean removeNode(Integer parNode,Integer node);
    boolean updateContent(Integer id, ContentDTO content);
    ContentDTO getContent(Integer id);
}
