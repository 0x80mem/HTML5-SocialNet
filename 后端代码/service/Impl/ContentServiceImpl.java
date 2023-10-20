package com.html.nds.service.impl;

import com.html.nds.entity.Content;
import com.html.nds.mapper.ContentMapper;
import com.html.nds.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

    @Override
    public boolean titleAsNumUpdate(Integer id,Integer value) {
        if(id==null||value==null)
            return false;
        Content content = getById(id);
        if(content==null)
            return false;
        try{
            //尝试转换成Integer并更新
            int title=Integer.parseInt(content.getTitle());
            title+=value;
            content.setTitle(Integer.toString(title));
            updateById(content);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
