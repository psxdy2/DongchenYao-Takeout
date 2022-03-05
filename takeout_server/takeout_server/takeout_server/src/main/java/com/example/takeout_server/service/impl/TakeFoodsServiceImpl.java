package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.TakeFoods;
import com.example.takeout_server.mapper.TakeFoodsMapper;
import com.example.takeout_server.service.ITakeFoodsService;
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
public class TakeFoodsServiceImpl extends ServiceImpl<TakeFoodsMapper, TakeFoods> implements ITakeFoodsService {

}
