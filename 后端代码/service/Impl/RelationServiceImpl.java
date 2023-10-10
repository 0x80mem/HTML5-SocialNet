package com.html.nds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.html.nds.common.GlobalException;
import com.html.nds.entity.NodeDTO;
import com.html.nds.entity.Post;
import com.html.nds.entity.Relation;
import com.html.nds.mapper.RelationMapper;
import com.html.nds.service.IPostService;
import com.html.nds.service.IRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.List;


@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements IRelationService {
    @Autowired
    RelationMapper relationMapper;
    @Override
    public boolean addChildNode(Integer parNode, Integer chiNode) {
        if(parNode==null||chiNode==null)
            return false;
        //不存在
        if(getOne(new LambdaQueryWrapper<Relation>()
                .eq(Relation::getParent,parNode)
                .eq(Relation::getChild,chiNode))==null)
            //sava
            return save(new Relation(parNode, chiNode));
        throw new GlobalException(0,"addChildNode:"+parNode+"-"+chiNode+"  已存在");
    }

    @Override
    public boolean removeChildNode(Integer parNode, Integer chiNode) {
        //已存在
        if(getOne(new LambdaQueryWrapper<Relation>()
                .eq(Relation::getParent,parNode)
                .eq(Relation::getChild,chiNode))!=null)
            //remove
            return remove(new LambdaQueryWrapper<Relation>().eq(Relation::getParent,parNode).eq(Relation::getChild,chiNode));
        throw new GlobalException(0,"removeChildNode:"+parNode+"-"+chiNode+"  不存在");
    }

    @Override
    public List<NodeDTO> getChiNodes(Integer parNode) {
        List<NodeDTO> nodeDTOS=relationMapper.getChiNodes(parNode);
        for(NodeDTO nodeDTO:nodeDTOS){
            nodeDTO.setParNode(parNode);
            System.out.println(nodeDTO);
        }
        return nodeDTOS;
    }
}
