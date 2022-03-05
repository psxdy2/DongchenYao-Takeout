package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.CollectStore;
import com.example.takeout_server.domain.TakeCollect;
import com.example.takeout_server.domain.TakeComment;
import com.example.takeout_server.domain.UserinfoComment;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.ICollectStoreService;
import com.example.takeout_server.service.ITakeCollectService;
import com.example.takeout_server.service.ITakeCommentService;
import com.example.takeout_server.service.IUserinfoCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论控制器
 *  Author:Dongchen Yao
 *  Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/comment")
public class CommentController {
    @Resource
    ITakeCommentService takeCommentService;
    @Resource
    IUserinfoCommentService userinfoCommentService;

    /**
     * 添加评论
     *
     * @return
     */
    @PostMapping("addComment")
    public JsonResult addComment(@RequestBody TakeComment takeComment) {
        if (takeComment.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (takeComment.getOrderid() == null) {
            return JsonResult.error("订单id不能为空");
        } else if (takeComment.getStoreid() == null) {
            return JsonResult.error("商铺id不能为空");
        } else if (takeComment.getComment() == null) {
            return JsonResult.error("评论内容不能为空");
        }
        if (takeCommentService.save(takeComment)) {
            return JsonResult.succeed("添加评论成功", takeComment);
        }
        return JsonResult.error("添加评论失败");
    }


    /**
     * 获取指定店铺下的所有评价
     */
    @GetMapping("getAllComment")
    public JsonResult getAllComment(String id) {
        if (id == null) {
            return JsonResult.error("店铺id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("storeid", id);
        List<UserinfoComment> result = userinfoCommentService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查找成功", result);
        }
        return JsonResult.error("查找失败");
    }

}
