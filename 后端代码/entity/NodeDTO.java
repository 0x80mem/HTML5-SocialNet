package com.html.nds.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class NodeDTO {

    private Integer id;

    private String type;

    private LocalDateTime time;

    private Integer parNode;

    private List<NodeDTO> chiNodes;

    private ContentDTO content;

}
