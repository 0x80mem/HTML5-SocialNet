package com.jlusw.html.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlusw.html.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

