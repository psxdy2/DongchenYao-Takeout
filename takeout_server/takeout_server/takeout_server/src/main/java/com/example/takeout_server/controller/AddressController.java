package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.*;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.ITakeAddressService;
import com.example.takeout_server.service.ITakeUserinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址信息控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/address")
public class AddressController {
    @Resource
    ITakeAddressService takeAddressService;

    /**
     * 添加地址
     * @return
     */
    @PostMapping("addaddress")
    public JsonResult addaddress(@RequestBody TakeAddress takeAddress) {
        if (takeAddress.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (takeAddress.getName() == null) {
            return JsonResult.error("收货人姓名不能为空");
        } else if (takeAddress.getPhone() == null) {
            return JsonResult.error("收货人电话不能为空");
        } else if (takeAddress.getAddress() == null) {
            return JsonResult.error("收货人地址不能为空");
        }
        if (takeAddressService.save(takeAddress)) {
            return JsonResult.succeed("添加成功", takeAddress);
        }
        return JsonResult.error("添加失败");
    }


    /**
     * 查询指定用户下的所有地址
     */
    @GetMapping("SearchAddress")
    public JsonResult SearchAddress(String id) {
        if (id == null) {
            return JsonResult.error("用户id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("userid", id);
        List<TakeAddress> result = takeAddressService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查询成功", result);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 删除地址
     */
    @GetMapping("deleteAddress")
    public JsonResult deleteFoodTypes(String id) {
        if (id == null) {
            return JsonResult.error("地址id不能为空");
        }
        if (takeAddressService.removeById(id)) {
            return JsonResult.succeed("删除成功", id);
        }
        return JsonResult.error("查询失败");
    }

}
