package com.jlusw.html.entity;

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
 * @author ShacooKL
 * @since 2023-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("like")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.NONE)
    private Integer userId;

    private Integer postId;

    private LocalDateTime time;


}
