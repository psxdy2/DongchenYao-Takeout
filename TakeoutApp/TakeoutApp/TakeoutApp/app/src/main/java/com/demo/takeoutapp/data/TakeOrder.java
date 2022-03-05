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
public class TakeOrder  {

    private Integer id;

    private Integer userid;

    private String price;

    private String address;

    private String createdtime;

    private String updatedtime;

    private String status;

    private String foods;

    private Integer storeid;

    private Integer storeuserid;

    private String sendtime;

    private String deliveryinfo;
}
