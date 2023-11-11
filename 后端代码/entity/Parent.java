package com.html.nds.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("parent")
public class Parent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "par_id", type = IdType.NONE)
    private Integer parId;

    private Integer id;

    private LocalDateTime time;

    public Parent(Integer parId, Integer id, LocalDateTime time) {
        this.parId = parId;
        this.id = id;
        this.time = time;
    }
}
