package com.html.nds.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private String title;

    private String content;

    public Content(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public Content(ContentDTO contentDTO) {
        this.id=0;
        this.title = contentDTO.getTitle();
        this.content = contentDTO.getContent();
    }
}
