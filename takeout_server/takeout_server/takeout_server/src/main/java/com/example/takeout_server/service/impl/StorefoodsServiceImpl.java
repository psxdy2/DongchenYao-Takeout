package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.Storefoods;
import com.example.takeout_server.mapper.StorefoodsMapper;
import com.example.takeout_server.service.IStorefoodsService;
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
public class StorefoodsServiceImpl extends ServiceImpl<StorefoodsMapper, Storefoods> implements IStorefoodsService {

}
