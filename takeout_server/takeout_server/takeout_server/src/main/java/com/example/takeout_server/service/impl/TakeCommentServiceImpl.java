package com.example.takeout_server.service.impl;

import com.example.takeout_server.domain.TakeComment;
import com.example.takeout_server.mapper.TakeCommentMapper;
import com.example.takeout_server.service.ITakeCommentService;
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
public class TakeCommentServiceImpl extends ServiceImpl<TakeCommentMapper, TakeComment> implements ITakeCommentService {

}
