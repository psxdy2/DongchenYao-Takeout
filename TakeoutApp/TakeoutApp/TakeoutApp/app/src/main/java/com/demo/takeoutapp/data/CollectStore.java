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
public class CollectStore {

    private Integer collectid;

    private Integer userid;

    private Integer storeid;

    private String storename;

    private String storeinfo;

    private String storeimage;


}
