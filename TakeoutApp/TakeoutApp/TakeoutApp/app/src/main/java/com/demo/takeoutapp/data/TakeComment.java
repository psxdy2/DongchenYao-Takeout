package com.demo.takeoutapp.data;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author moyu
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TakeComment {

    private Integer id;

    private Integer userid;

    private Integer storeid;

    private Integer orderid;

    private String comment;

    private LocalDateTime createdtime;

    private LocalDateTime updatedtime;


}
