package com.example.takeout_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.takeout_server.domain.TakeStore;
import com.example.takeout_server.domain.TakeUserinfo;
import com.example.takeout_server.entity.JsonResult;
import com.example.takeout_server.service.ITakeUserinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户登陆信息控制器
 * Author:Dongchen Yao
 * Date:2021.05.21
 */
@RestController
@RequestMapping("/takeout_server/user")
public class UserController {
    @Resource
    ITakeUserinfoService userinfoService;

    /**
     * 登陆
     */
    @PostMapping("login")
    public JsonResult login(@RequestBody TakeUserinfo userinfo) {
        if (userinfo.getAccount() == null) {
            return JsonResult.error("账号不能为空");
        } else if (userinfo.getPassword() == null) {
            return JsonResult.error("密码不能为空");
        }
        QueryWrapper<TakeUserinfo> queryWrapper = new QueryWrapper<TakeUserinfo>();
        queryWrapper.eq("account", userinfo.getAccount());
        queryWrapper.eq("password", userinfo.getPassword());
        TakeUserinfo result = userinfoService.getOne(queryWrapper);
        if (result != null) {
            return JsonResult.succeed("登陆成功", result);
        }
        return JsonResult.error("登陆失败");
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public JsonResult register(@RequestBody TakeUserinfo userinfo) {
        if (userinfo.getAccount() == null) {
            return JsonResult.error("账号不能为空");
        } else if (userinfo.getPassword() == null) {
            return JsonResult.error("密码不能为空");
        } else if (userinfo.getUsername() == null) {
            return JsonResult.error("昵称不能为空");
        }
        //默认头像
        userinfo.setPortrait("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2915828601,496412029&fm=26&gp=0.jpg");
        try {
            if (userinfoService.save(userinfo)) {
                return JsonResult.succeed("注册成功", userinfo);
            }
        }catch (Exception e){
            return JsonResult.error("注册失败");
        }
        return JsonResult.error("注册失败");
    }

    /**
     * 修改个人信息
     */
    @PostMapping("UpdateUserInfo")
    public JsonResult UpdateUserInfo(@RequestBody TakeUserinfo userinfo){
        if (userinfoService.updateById(userinfo)){
            return JsonResult.succeed("修改成功", userinfo);
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 获取所有用户信息
     */
    @GetMapping("SearchAllUser")
    public JsonResult SearchAllUser() {
        List<TakeUserinfo> result = userinfoService.list();
        if (result != null) {
            return JsonResult.succeed("查询用户成功", result);
        }
        return JsonResult.error("查询用户失败");
    }

}
