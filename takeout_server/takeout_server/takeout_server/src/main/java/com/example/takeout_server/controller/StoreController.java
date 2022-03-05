package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeout_server.domain.*;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺信息控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/store")
public class StoreController {
    @Resource
    ITakeStoreService takeStoreService;
    @Resource
    ITakeFoodtypeService takeFoodtypeService;
    @Resource
    ITakeFoodsService takeFoodsService;

    @GetMapping("test")
    public String test(String msg) {
        return msg;
    }

    /**
     * 查询指定账号下的店铺信息
     */
    @GetMapping("SearchStore")
    public JsonResult SearchStore(String id) {
        if (id == null) {
            return JsonResult.error("用户id不能为空");
        }
        QueryWrapper<TakeStore> queryWrapper = new QueryWrapper<TakeStore>();
        queryWrapper.eq("userid", id);
        TakeStore result = takeStoreService.getOne(queryWrapper);
        if (result != null) {
            return JsonResult.succeed("查询成功", result);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 创建店铺
     */
    @PostMapping("addstore")
    public JsonResult addStore(@RequestBody Storefoods storefoods) {
        if (storefoods.getUserid() == null) {
            return JsonResult.error("用户id不能为空");
        } else if (storefoods.getStorename() == null) {
            return JsonResult.error("店铺名称不能为空");
        } else if (storefoods.getStoreinfo() == null) {
            return JsonResult.error("店铺介绍不能为空");
        } else if (storefoods.getStoreimage() == null) {
            return JsonResult.error("店铺logo不能为空");
        }
        //检查当前账号下是否存在店铺
        QueryWrapper<TakeStore> queryWrapper = new QueryWrapper<TakeStore>();
        queryWrapper.eq("userid", storefoods.getUserid());
        TakeStore result = takeStoreService.getOne(queryWrapper);
        if (result != null) {
            return JsonResult.error("店铺已经存在");
        }
        //组装一个store对象
        TakeStore takeStore = new TakeStore();
        takeStore.setUserid(storefoods.getUserid());
        takeStore.setStorename(storefoods.getStorename());
        takeStore.setStoreinfo(storefoods.getStoreinfo());
        takeStore.setStoreimage(storefoods.getStoreimage());
        if (takeStoreService.save(takeStore)) {
            return JsonResult.succeed("开铺成功", storefoods);
        }
        return JsonResult.error("开铺失败");
    }

    /**
     * 增加分类
     * 根据店铺id来确定要增加哪个店铺的分类
     */
    @PostMapping("addtype")
    public JsonResult addType(@RequestBody Storefoods storefoods) {
        if (storefoods.getStroeid() == null) {
            return JsonResult.error("店铺id不能为空");
        } else if (storefoods.getTypename() == null) {
            return JsonResult.error("店铺分类不能为空");
        }
        //组装一个分类对象
        TakeFoodtype takeFoodtype = new TakeFoodtype();
        takeFoodtype.setStoreid(storefoods.getStroeid());
        takeFoodtype.setTypename(storefoods.getTypename());
        if (takeFoodtypeService.save(takeFoodtype)) {
            return JsonResult.succeed("增加分类成功", storefoods);
        }
        return JsonResult.error("增加分类失败");
    }

    /**
     * 增加菜品
     */
    @PostMapping("addfood")
    public JsonResult addFood(@RequestBody Storefoods storefoods) {
        if (storefoods.getFoodtypeid() == null) {
            return JsonResult.error("分类id不能为空");
        } else if (storefoods.getFoodname() == null) {
            return JsonResult.error("菜品名称不能为空");
        } else if (storefoods.getFoodinfo() == null) {
            return JsonResult.error("菜品描述不能为空");
        } else if (storefoods.getFoodprice() == null) {
            return JsonResult.error("菜品价格不能为空");
        } else if (storefoods.getFoodnum() == null) {
            return JsonResult.error("菜品库存不能为空");
        }
        if (storefoods.getFoodimage() == null){
            storefoods.setFoodimage("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1675715075,61394264&fm=26&gp=0.jpg");
        }
        if (storefoods.getFoodstock() == null){
            storefoods.setFoodstock("-1");
        }
        //组装一个菜品对象
        TakeFoods takeFoods = new TakeFoods();
        takeFoods.setFoodtypeid(storefoods.getFoodtypeid());
        takeFoods.setFoodname(storefoods.getFoodname());
        takeFoods.setFoodinfo(storefoods.getFoodinfo());
        takeFoods.setFoodimage(storefoods.getFoodimage());
        takeFoods.setFoodprice(storefoods.getFoodprice());
        takeFoods.setFoodstock(storefoods.getFoodstock());
        takeFoods.setFoodnum(storefoods.getFoodnum());
        if (takeFoodsService.save(takeFoods)) {
            return JsonResult.succeed("增加菜品成功", takeFoods);
        }
        return JsonResult.error("增加菜品失败");
    }

    /**
     * 查询指定店铺下的所有类别
     */
    @GetMapping("SearchFoodTypes")
    public JsonResult SearchFoodTypes(String id) {
        if (id == null) {
            return JsonResult.error("店铺id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("storeid", id);
        List<TakeFoodtype> result = takeFoodtypeService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查询成功", result);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 查询指定类别下的所有菜品
     */
    @GetMapping("SearchFoods")
    public JsonResult SearchFoods(String id) {
        if (id == null) {
            return JsonResult.error("类别id不能为空");
        }
        Wrapper wrapper = new QueryWrapper<>().eq("foodtypeid", id);
        List<TakeFoods> result = takeFoodsService.list(wrapper);
        if (result != null) {
            return JsonResult.succeed("查询成功", result);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 删除类别
     */
    @GetMapping("deleteFoodTypes")
    public JsonResult deleteFoodTypes(String id) {
        if (id == null) {
            return JsonResult.error("类别id不能为空");
        }
        if (takeFoodtypeService.removeById(id)) {
            return JsonResult.succeed("删除成功", id);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 删除菜品
     */
    @GetMapping("deleteFoods")
    public JsonResult deleteFoods(String id) {
        if (id == null) {
            return JsonResult.error("菜品id不能为空");
        }
        if (takeFoodsService.removeById(id)) {
            return JsonResult.succeed("删除成功", id);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 查询所有店铺
     */
    @GetMapping("SearchAllStore")
    public JsonResult SearchAllStore() {
        List<TakeStore> result = takeStoreService.list();
        if (result != null) {
            return JsonResult.succeed("查询成功", result);
        }
        return JsonResult.error("查询失败");
    }

    /**
     * 删除店铺
     * 管理员操作
     */
    @GetMapping("removeStore")
    public JsonResult removeStore(String id) {
        if (id == null) {
            return JsonResult.error("店铺id不能为空");
        }
        if (takeStoreService.removeById(id)) {
            return JsonResult.succeed("删除店铺成功", id);
        }
        return JsonResult.error("查询店铺失败");
    }

}
