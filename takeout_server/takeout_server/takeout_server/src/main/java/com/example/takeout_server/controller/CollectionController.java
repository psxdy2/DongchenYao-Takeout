package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.*;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.ICollectStoreService;
import com.example.takeout_server.service.ITakeCollectService;
import com.example.takeout_server.service.ITakeOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 收藏控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/collection")
public class CollectionController {
    @Resource
    ITakeCollectService takeCollectService;
    @Resource
    ICollectStoreService collectStoreService;

    /**
     * 获取指定用户的所有收藏店铺
     */
    @GetMapping("getCollection")
    public JsonResult getCollection(String id) {
        if (id == null) {
            return JsonResult.error("用户id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("userid", id);
        List<CollectStore> result = collectStoreService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查找成功", result);
        }
        return JsonResult.error("查找失败");
    }

    /**
     * 添加收藏
     * @return
     */
    @PostMapping("addCollection")
    public JsonResult addCollection(@RequestBody TakeCollect takeCollect) {
        if (takeCollect.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (takeCollect.getStoreid() == null) {
            return JsonResult.error("商铺id不能为空");
        }
        if (takeCollectService.save(takeCollect)) {
            return JsonResult.succeed("添加收藏成功", takeCollect);
        }
        return JsonResult.error("添加收藏失败");
    }

    /**
     * 取消收藏
     */
    @PostMapping("removeCollection")
    public JsonResult removeCollection(@RequestBody TakeCollect takeCollect) {
        if (takeCollect.getId() == null) {
            return JsonResult.error("id不能为空");
        }
        if (takeCollectService.removeById(takeCollect)) {
            return JsonResult.succeed("取消收藏成功", takeCollect);
        }
        return JsonResult.error("取消收藏失败");
    }

    /**
     * 检查是否收藏
     */
    @PostMapping("SearchCollection")
    public JsonResult SearchCollection(@RequestBody TakeCollect takeCollect) {
        if (takeCollect.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (takeCollect.getStoreid() == null) {
            return JsonResult.error("商铺id不能为空");
        }
        QueryWrapper<TakeCollect> queryWrapper = new QueryWrapper<TakeCollect>();
        queryWrapper.eq("userid", takeCollect.getUserid());
        queryWrapper.eq("storeid", takeCollect.getStoreid());
        TakeCollect result = takeCollectService.getOne(queryWrapper);
        if (result != null) {
            return JsonResult.succeed("查找是否收藏成功", result);
        }
        return JsonResult.error("查找是否收藏失败");
    }

}
