package com.example.takeout_server.domain;

import java.time.LocalDateTime;
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
public class StoreOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("storeuserid")
    private Integer storeuserid;

    @TableField("orderid")
    private Integer orderid;

    @TableField("userid")
    private Integer userid;

    @TableField("price")
    private String price;

    @TableField("address")
    private String address;

    @TableField("createdtime")
    private LocalDateTime createdtime;

    @TableField("updatedtime")
    private LocalDateTime updatedtime;

    @TableField("status")
    private String status;

    @TableField("foods")
    private String foods;

    @TableField("storeid")
    private Integer storeid;

    @TableField("sendtime")
    private String sendtime;

    @TableField("deliveryinfo")
    private String deliveryinfo;


}
