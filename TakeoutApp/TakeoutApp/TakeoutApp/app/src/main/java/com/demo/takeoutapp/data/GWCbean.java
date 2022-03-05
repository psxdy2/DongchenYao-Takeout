package com.demo.takeoutapp.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GWCbean {
    private Integer id;
    private Integer num;
    private TakeFoods takeFoods;
}
