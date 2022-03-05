package com.example.takeout_server.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.CollectStore;
import com.example.takeout_server.domain.StoreOrder;
import com.example.takeout_server.domain.TakeFoods;
import com.example.takeout_server.domain.TakeOrder;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.IStoreOrderService;
import com.example.takeout_server.service.ITakeFoodsService;
import com.example.takeout_server.service.ITakeOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/order")
public class OrderController {
    @Resource
    ITakeOrderService takeOrderService;
    @Resource
    IStoreOrderService storeOrderService;
    @Resource
    ITakeFoodsService foodsService;

    /**
     * 添加订单
     *
     * @return
     */
    @PostMapping("addOrder")
    public JsonResult addOrder(@RequestBody TakeOrder takeOrder) {
        if (takeOrder.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (takeOrder.getStoreid() == null) {
            return JsonResult.error("店铺id不能为空");
        } else if (takeOrder.getAddress() == null) {
            return JsonResult.error("收货人地址不能为空");
        } else if (takeOrder.getPrice() == null) {
            return JsonResult.error("菜品价格不能为空");
        } else if (takeOrder.getFoods() == null) {
            return JsonResult.error("菜品信息不能为空");
        }
        //修改库存
        JSONArray array = JSONArray.parseArray(takeOrder.getFoods());
        for (int i = 0; i < array.size(); i++) {
            TakeFoods takeFoods = new TakeFoods();
            takeFoods.setId(array.getJSONObject(i).getInteger("id"));
            takeFoods.setFoodnum(foodsService.getById(array.getJSONObject(i).getInteger("id")).getFoodnum());
            //减
            if (takeFoods.getFoodnum() < array.getJSONObject(i).getInteger("num")){
                return JsonResult.error("库存不足");
            }
            int num = takeFoods.getFoodnum() - array.getJSONObject(i).getInteger("num");
            takeFoods.setFoodnum(num);
            foodsService.updateById(takeFoods);
        }
        if (takeOrderService.save(takeOrder)) {
            return JsonResult.succeed("添加订单成功", takeOrder);
        }
        return JsonResult.error("添加订单失败");
    }

    /**
     * 获取指定用户的所有订单
     */
    @GetMapping("getAllOrder")
    public JsonResult getAllOrder(String id) {
        if (id == null) {
            return JsonResult.error("用户id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("userid", id);
        List<StoreOrder> result = storeOrderService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查找成功", result);
        }
        return JsonResult.error("查找失败");
    }

    /**
     * 获取指定店铺的所有订单
     */
    @GetMapping("getStoreAllOrder")
    public JsonResult getStoreAllOrder(String id) {
        if (id == null) {
            return JsonResult.error("店铺id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("storeid", id);
        List<CollectStore> result = takeOrderService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查找成功", result);
        }
        return JsonResult.error("查找失败");
    }

    /**
     * 获取所有订单
     */
    @GetMapping("getOrder")
    public JsonResult getOrder() {
        List<TakeOrder> result = takeOrderService.list();
        if (result != null) {
            return JsonResult.succeed("查找成功", result);
        }
        return JsonResult.error("查找失败");
    }

    /**
     * 修改订单状态
     */
    @PostMapping("updateOrder")
    public JsonResult updateOrder(@RequestBody TakeOrder takeOrder) {
        if (takeOrder.getId() == null) {
            return JsonResult.error("id不能为空");
        } else if (takeOrder.getStatus() == null) {
            return JsonResult.error("状态不能为空");
        }
        if (takeOrderService.updateById(takeOrder)) {
            return JsonResult.succeed("修改订单成功", takeOrder);
        }
        return JsonResult.error("修改订单失败");
    }

}
