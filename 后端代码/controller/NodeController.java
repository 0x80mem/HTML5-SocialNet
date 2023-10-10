package com.html.nds.controller;

import com.html.nds.common.DTOUtil;
import com.html.nds.common.R;
import com.html.nds.entity.Content;
import com.html.nds.entity.ContentDTO;
import com.html.nds.entity.NodeDTO;
import com.html.nds.service.IContentService;
import com.html.nds.service.INodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
@Transactional
public class NodeController {
    @Autowired
    INodeService nodeService;
    @Autowired
    IContentService contentService;
    @PostMapping("/createNode")
    public R<String> createNode(@RequestBody NodeDTO nodeDTO) {

        Integer id = nodeService.createNode(nodeDTO.getId(), nodeDTO.getType());

        if(id==null)
            return R.error();
        if (nodeDTO.getContent() != null){
            Content content_= DTOUtil.DTOToContent(nodeDTO.getContent());
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

}
