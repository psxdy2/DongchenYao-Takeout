package com.example.takeout_server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TakeStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userid")
    private Integer userid;

    @TableField("storename")
    private String storename;

    @TableField("storeinfo")
    private String storeinfo;

    @TableField("storeimage")
    private String storeimage;

    @TableField("createdtime")
    private LocalDateTime createdtime;

    @TableField("updatedtime")
    private LocalDateTime updatedtime;


}
