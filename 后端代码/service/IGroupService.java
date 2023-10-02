package com.jlusw.html.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface IGroupService<T>  {
    /** 创建分组*/
    boolean createGroup(Long userId,String groupName);
    /** 删除分组*/
    boolean deleteGroup(Long userId,String groupName);
    /** 更改分组名称*/
    boolean changeGroupName(Long userId,String oldGroupName,String newGroupName);
    /** 检查分组是否存在*/
    boolean isExistGroup(Long userId,String groupName);
    /** 获取所有分组名称*/
    List<String> getGroupNames(Long userId);
    /** 分组新增内容*/
    boolean insert(Long userId,Long goalId,String groupName);
    /** 分组移除内容*/
    boolean remove(Long userId,Long goalId,String groupName);
    /** 改变所在分组*/
    boolean moveTo(Long userId,Long goalId,String oldGroupName,String newGroupName);
    /** 获取分组某页内容*/
    IPage<T> getGroupContent(Long userId,String groupName,Long startNum,Long pageSize);
    /** 获取分组所有内容*/
    List<Object> shareGroup(Long useId, String groupName);

}
