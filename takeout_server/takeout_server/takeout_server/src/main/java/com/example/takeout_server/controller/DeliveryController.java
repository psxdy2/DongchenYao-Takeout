package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.StoreOrder;
import com.example.takeout_server.domain.TakeDelivery;
import com.example.takeout_server.domain.TakeUserinfo;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.ITakeDeliveryService;
import com.example.takeout_server.service.ITakeUserinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 配送员信息控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/delivery")
public class DeliveryController {
    @Resource
    ITakeDeliveryService deliveryService;
    @Resource
    ITakeUserinfoService userinfoService;

    /**
     * 添加配送员
     */
    @PostMapping("addDelivery")
    public JsonResult addDelivery(@RequestBody TakeUserinfo userinfo) {
        if (userinfo.getId() == null) {
            return JsonResult.error("id不能为空");
        }
        TakeDelivery takeDelivery = new TakeDelivery();
        takeDelivery.setUserid(userinfo.getId());
        if (deliveryService.save(takeDelivery)) {
            return JsonResult.succeed("申请成功", takeDelivery);
        }
        return JsonResult.error("申请失败");
    }

    /**
     * 随机获取一个配送员
     */
    @GetMapping("getOneDelivery")
    public JsonResult getOneDelivery() {
        List<TakeDelivery> list = deliveryService.list();
        if (list.size() == 0){
            return JsonResult.error("获取失败");
        }
        int index = new Random().nextInt(list.size());
        TakeDelivery takeDelivery = list.get(index);
        //获取配送员详情
        TakeUserinfo userinfo = userinfoService.getById(takeDelivery.getUserid());
        if (userinfo != null) {
            return JsonResult.succeed("获取成功", userinfo);
        }
        return JsonResult.error("获取失败");
    }

    @GetMapping("queryDelivery")
    public JsonResult queryDelivery(String userid) {
        if (userid == null) {
            return JsonResult.error("userid不能为空");
        }
        QueryWrapper<TakeDelivery> queryWrapper = new QueryWrapper<TakeDelivery>();
        queryWrapper.eq("userid", userid);
        TakeDelivery takeDelivery = deliveryService.getOne(queryWrapper);
        if (takeDelivery == null) {
            return JsonResult.succeed("此用户可以申请骑手", takeDelivery);
        }
        return JsonResult.error("此用户不可以申请骑手");
    }

    /**
     * 获取所有数据
     */
    @GetMapping("getAllDelivery")
    public JsonResult getAllDelivery() {
        List<TakeDelivery> result = deliveryService.list();
        List<TakeUserinfo> takeUserinfos = new ArrayList<>();
        for (int i=0;i<result.size();i++){
            takeUserinfos.add(userinfoService.getById(result.get(i).getUserid()));
        }
        if (takeUserinfos != null) {
            return JsonResult.succeed("查找成功", takeUserinfos);
        }
        return JsonResult.error("查找失败");
    }

    /**
     * 删除骑手
     * 管理员操作
     */
    @GetMapping("removeDelivery")
    public JsonResult removeDelivery(String userid) {
        if (userid == null) {
            return JsonResult.error("userid不能为空");
        }
        QueryWrapper<TakeDelivery> queryWrapper = new QueryWrapper<TakeDelivery>();
        queryWrapper.eq("userid", userid);
        if (deliveryService.remove(queryWrapper)) {
            return JsonResult.succeed("删除骑手成功", userid);
        }
        return JsonResult.error("查询骑手失败");
    }

}
