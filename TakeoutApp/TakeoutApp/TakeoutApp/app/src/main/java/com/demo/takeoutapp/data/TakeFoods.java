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
public class TakeFoods {

    private Integer id;

    private Integer foodtypeid;

    private String foodname;

    private String foodinfo;

    private String foodprice;

    private String foodstock;

    private String foodimage;

    private LocalDateTime createdtime;

    private Integer foodnum;

}
