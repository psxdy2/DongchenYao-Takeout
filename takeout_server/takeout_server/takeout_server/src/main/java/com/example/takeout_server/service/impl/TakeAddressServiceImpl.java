package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.TakeAddress;
import com.example.takeout_server.mapper.TakeAddressMapper;
import com.example.takeout_server.service.ITakeAddressService;
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
public class TakeAddressServiceImpl extends ServiceImpl<TakeAddressMapper, TakeAddress> implements ITakeAddressService {

}
