package com.demo.takeoutapp.data;

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
public class TakeStore {

    private Integer id;

    private Integer userid;

    private String storename;

    private String storeinfo;

    private String storeimage;

}
