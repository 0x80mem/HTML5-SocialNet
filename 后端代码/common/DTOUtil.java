package com.html.nds.common;

import com.html.nds.entity.*;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

public class DTOUtil {

    public static MapperFactory userToDTOMapperFactory(){
        //获取MapperFactory
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        //MapperFactory设置映射
        mapperFactory.classMap(User.class, UserDTO.class)
                .field("id","userId")//名称不同时映射
                .field("name","userName")
                .byDefault()//默认
                .register();
        return mapperFactory;
    }

    /** User 转 UserDTO */
    public static UserDTO toUserDTO(User user){
        return userToDTOMapperFactory().getMapperFacade().map(user,UserDTO.class);
    }
    /**Users 转 UserDTOs */
    public static List<UserDTO> toUserDTOs(List<User> users){
        return userToDTOMapperFactory().getMapperFacade().mapAsList(users,UserDTO.class);
    }

    public static  Post DTOToPost(PostDTO postDTO){
        return new DefaultMapperFactory.Builder().build().getMapperFacade().map(postDTO,Post.class);
    }
    public static  List<Post> DTOToPost( List<PostDTO> postDTOs){
        return new DefaultMapperFactory.Builder().build().getMapperFacade().mapAsList(postDTOs,Post.class);
    }

    public static Content DTOToContent(ContentDTO contentDTO){
        return new DefaultMapperFactory.Builder().build().getMapperFacade().map(contentDTO,Content.class);
    }
    public static NodeDTO nodeToDTO(Node node){
        return new DefaultMapperFactory.Builder().build().getMapperFacade().map(node,NodeDTO.class);
    }
}

