package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.TakeOrder;
import com.example.takeout_server.mapper.TakeOrderMapper;
import com.example.takeout_server.service.ITakeOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author moyu
 * @since 2021-05-25
 */
@Service
public class TakeOrderServiceImpl extends ServiceImpl<TakeOrderMapper, TakeOrder> implements ITakeOrderService {

}
