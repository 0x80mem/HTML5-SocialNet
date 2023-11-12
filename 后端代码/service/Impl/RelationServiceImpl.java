package com.html.nds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.html.nds.common.GlobalException;
import com.html.nds.entity.Parent;
import com.html.nds.entity.Relation;
import com.html.nds.mapper.ParentMapper;
import com.html.nds.mapper.RelationMapper;
import com.html.nds.service.IRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements IRelationService {
    @Autowired
    RelationMapper relationMapper;
    @Autowired
    ParentMapper parentMapper;
    //增加子节点
    @Override
    public void addChildNode(Integer parNode, Integer chiNode) {
        if(parNode==null||chiNode==null)
            throw new GlobalException(0,"参数错误");
        //不存在
        if(getOne(new LambdaQueryWrapper<Relation>()
                .eq(Relation::getParent,parNode)
                .eq(Relation::getChild,chiNode))==null)
            //sava
        {
            save(new Relation(parNode, chiNode));
            return;
        }
        throw new GlobalException(0 ,"重复操作");
    }
    //删除子节点
    @Override
    public void removeChildNode(Integer parNode, Integer chiNode) {
        //已存在
        if(getOne(new LambdaQueryWrapper<Relation>()
                .eq(Relation::getParent,parNode)
                .eq(Relation::getChild,chiNode))!=null)
            //remove
        {
            remove(new LambdaQueryWrapper<Relation>().eq(Relation::getParent, parNode).eq(Relation::getChild, chiNode));
            parentMapper.delete(new LambdaQueryWrapper<Parent>().eq(Parent::getParId, parNode).eq(Parent::getId, chiNode));
            return;
        }
        throw new GlobalException(0,"removeChildNode:"+parNode+"-"+chiNode+"  不存在");
    }

    @Override
    public void addParNode(Integer parNodeId, Integer chiNodeId) {
        System.out.println(parNodeId+" "+chiNodeId);
        Parent p = new Parent(parNodeId,chiNodeId, LocalDateTime.now());
        System.out.println(p);
        parentMapper.insert(p);
    }

    //获取子节点IDList
    @Override
    public List<Integer> getChiNodes(Integer parNode) {
        return relationMapper.getChiNodes(parNode);
    }
    //获取父节点IDList
    @Override
    public List<Integer> getParNodes(Integer chiNodeId) {
        return relationMapper.getParNodes(chiNodeId);
    }
}
