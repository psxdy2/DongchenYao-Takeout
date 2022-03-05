package com.demo.takeoutapp.data;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TakeDelivery{

    private Integer id;

    private Integer userid;

}
