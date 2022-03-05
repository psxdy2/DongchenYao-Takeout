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
public class TakeAddress {
    private Integer id;

    private Integer userid;

    private String address;

    private LocalDateTime createdtime;

    private LocalDateTime updatedtime;

    private String phone;

    private String name;

}
