package com.example.takeout_server.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Storefoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("stroeid")
    private Integer stroeid;

    @TableField("userid")
    private Integer userid;

    @TableField("storename")
    private String storename;

    @TableField("storeinfo")
    private String storeinfo;

    @TableField("storeimage")
    private String storeimage;

    @TableField("foodtypeid")
    private Integer foodtypeid;

    @TableField("typename")
    private String typename;

    @TableField("foodid")
    private Integer foodid;

    @TableField("foodname")
    private String foodname;

    @TableField("foodinfo")
    private String foodinfo;

    @TableField("foodprice")
    private String foodprice;

    @TableField("foodstock")
    private String foodstock;

    @TableField("foodimage")
    private String foodimage;

    @TableField("foodnum")
    private Integer foodnum;


}
