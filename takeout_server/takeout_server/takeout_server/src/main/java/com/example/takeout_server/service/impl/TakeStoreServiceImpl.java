package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.TakeStore;
import com.example.takeout_server.mapper.TakeStoreMapper;
import com.example.takeout_server.service.ITakeStoreService;
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
public class TakeStoreServiceImpl extends ServiceImpl<TakeStoreMapper, TakeStore> implements ITakeStoreService {

}
