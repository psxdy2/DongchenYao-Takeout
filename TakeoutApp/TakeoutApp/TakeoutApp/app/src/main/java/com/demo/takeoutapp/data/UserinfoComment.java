package com.demo.takeoutapp.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author moyu
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserinfoComment {

    private String username;

    private String account;

    private String portrait;

    private Integer userid;

    private Integer storeid;

    private Integer orderid;

    private String comment;

}
