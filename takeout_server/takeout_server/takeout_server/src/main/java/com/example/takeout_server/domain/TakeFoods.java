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
public class TakeFoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("foodtypeid")
    private Integer foodtypeid;

    @TableField("foodname")
    private String foodname;

    @TableField("foodinfo")
    private String foodinfo;

    @TableField("foodprice")
    private String foodprice;

    @TableField("foodstock")
    private String foodstock;

    @TableField("createdtime")
    private LocalDateTime createdtime;

    @TableField("updatedtime")
    private LocalDateTime updatedtime;

    @TableField("foodimage")
    private String foodimage;

    @TableField("foodnum")
    private Integer foodnum;


}
