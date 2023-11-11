package com.html.nds.controller;

import com.html.nds.common.DTOUtil;
import com.html.nds.common.R;
import com.html.nds.entity.*;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
@Transactional
@CrossOrigin
@Configuration
public class NodeController {
    @Autowired
    INodeService nodeService;
    @Autowired
    IContentService contentService;
    @PostMapping("/createNode")
    public R<String> createNode(@RequestBody PostV postV) {
        Integer id = nodeService.createNode(postV.getParPost().get(0), postV.getType(),postV.getAuthor(),true);
        if(id==null)
            return R.error();
        if (postV.getContent() != null){
            Content content_= DTOUtil.DTOToContent(postV.getContent());
            content_.setId(id);
            return R.result(contentService.save(content_));
        }
        return R.success();
    }

    @DeleteMapping("/removeNode")
    public R<String> removeNode(Integer parId, Integer nodeId) {
        if (nodeService.removeNode(parId, nodeId))
            return R.success();
        return R.error();
    }

    @PostMapping("/updateContent")
    public R<String> updateContent(@RequestBody NodeDTO nodeDTO) {
        System.out.println(nodeDTO);
        if (nodeService.updateContent(nodeDTO.getId(), nodeDTO.getContent()))
            return R.success();
        return R.error();
    }

    @GetMapping("/getContent")
    public R<ContentDTO> getContent(Integer id) {
        ContentDTO contentDTO = nodeService.getContent(id);
        if (contentDTO != null)
            return R.success(contentDTO);
        return R.error();
    }
    @GetMapping("/getNode")
    public R<PostV> getNode(Integer id) {
        PostV postV = nodeService.getNode(id);
        if(postV==null)
            return R.error();
        return R.success(postV);
    }

}
