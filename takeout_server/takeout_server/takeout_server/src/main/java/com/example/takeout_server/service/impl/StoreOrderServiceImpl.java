package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.StoreOrder;
import com.example.takeout_server.mapper.StoreOrderMapper;
import com.example.takeout_server.service.IStoreOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author moyu
 * @since 2021-05-25
 */
@Service
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderMapper, StoreOrder> implements IStoreOrderService {

}
