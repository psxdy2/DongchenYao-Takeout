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
public class CollectStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("collectid")
    private Integer collectid;

    @TableField("userid")
    private Integer userid;

    @TableField("storeid")
    private Integer storeid;

    @TableField("storename")
    private String storename;

    @TableField("storeinfo")
    private String storeinfo;

    @TableField("storeimage")
    private String storeimage;


}
