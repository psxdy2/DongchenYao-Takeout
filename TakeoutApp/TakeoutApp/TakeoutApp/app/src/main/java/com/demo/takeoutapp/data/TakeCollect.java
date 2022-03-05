package com.demo.takeoutapp.data;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class TakeCollect implements Serializable {

    private Integer id;

    private Integer userid;

    private Integer storeid;

    private LocalDateTime createdtime;

    private LocalDateTime updatedtime;


}
