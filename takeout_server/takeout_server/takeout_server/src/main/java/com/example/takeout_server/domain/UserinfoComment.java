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
public class UserinfoComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("account")
    private String account;

    @TableField("portrait")
    private String portrait;

    @TableField("userid")
    private Integer userid;

    @TableField("storeid")
    private Integer storeid;

    @TableField("orderid")
    private Integer orderid;

    @TableField("comment")
    private String comment;


}
