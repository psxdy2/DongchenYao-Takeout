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
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TakeFoodtype {
    private Integer id;

    private Integer storeid;

    private String typename;

    private LocalDateTime createdtime;

    private LocalDateTime updatedtime;

}
