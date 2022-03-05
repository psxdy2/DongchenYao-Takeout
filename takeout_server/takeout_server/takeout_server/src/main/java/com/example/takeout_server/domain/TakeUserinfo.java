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
public class TakeUserinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("portrait")
    private String portrait;

    @TableField("createdtime")
    private LocalDateTime createdtime;

    @TableField("updatedtime")
    private LocalDateTime updatedtime;

    @TableField("sex")
    private String sex;

    @TableField("birthday")
    private String birthday;


}
