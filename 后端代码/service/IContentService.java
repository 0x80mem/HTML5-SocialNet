package com.html.nds.service;

import com.html.nds.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IContentService extends IService<Content> {
    boolean titleAsNumUpdate(Integer id,Integer value);

}
