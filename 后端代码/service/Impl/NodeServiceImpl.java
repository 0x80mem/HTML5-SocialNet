package com.html.nds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.html.nds.common.DTOUtil;
import com.html.nds.entity.Content;
import com.html.nds.entity.ContentDTO;
import com.html.nds.entity.Node;
import com.html.nds.entity.Relation;
import com.html.nds.mapper.NodeMapper;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.html.nds.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements INodeService {
    @Autowired
    IRelationService relationService;
    @Autowired
    IContentService contentService;
    @Override
    public Integer generateId() {
        Integer s;
        do {
            s = Math.abs(UUID.randomUUID().hashCode());
        } while (getById(s) != null);
        return s;
    }

    @Override
    public Integer createNode(Integer parNode, String type) {
        Integer uid = generateId();
        Node node = new Node(uid, type);
        if (save(node)) {
            if (parNode != null){
                relationService.addChildNode(parNode, uid);
            }
            return uid;
        }
        return null;
    }

    @Override
    public Integer createNode(Integer parNode, String type, ContentDTO contentDTO) {
        Integer nodeId =  createNode(parNode,type);
        Content content = DTOUtil.DTOToContent(contentDTO);
        content.setId(nodeId);
        contentService.save(content);
        return nodeId;
    }

    @Override
    public boolean removeNode(Integer parNode, Integer node) {
        if(node==null)
            return false;
        relationService.remove(new QueryWrapper<Relation>().eq("parent", node));
        if(parNode!=null)
            relationService.remove(new QueryWrapper<Relation>().eq("parent", parNode).eq("child", node));
        contentService.removeById(node);
        return removeById(node);
    }

    @Override
    public boolean updateContent(Integer id, ContentDTO contentDTO) {
        //查询content是否存在
        Content content = contentService.getById(id);
        if (content == null)
            return false;
        //更新content
        content.setContent(contentDTO.getContent());
        content.setTitle(contentDTO.getTitle());
        return contentService.updateById(content);
    }
    @Override
    public ContentDTO getContent(Integer id){
        //查询content是否存在
        Content content = contentService.getById(id);
        if (content == null)
            return null;
        ContentDTO contentDTO=new ContentDTO();
        contentDTO.setTitle(content.getTitle());
        contentDTO.setContent(content.getContent());
        return contentDTO;
    }
}
