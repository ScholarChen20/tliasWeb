package com.itheima.controller;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.itheima.pojo.*;
import com.itheima.pojo.dto.LoginInfo;
import com.itheima.pojo.dto.UserLoginInfo;
import com.itheima.pojo.dto.WxLoginInfo;
import com.itheima.service.EmpService;
import com.itheima.service.UserService;
import com.itheima.service.impl.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private WechatService wechatService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmpService empService;
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("login emp: {}", emp);
        LoginInfo loginInfo = empService.login(emp);
        if (loginInfo == null) {
            return Result.error("登录失败,用户名或密码错误");
        }else{
            return Result.success(loginInfo);
        }
    }

    @PostMapping("/userLogin")
    public Result login(@RequestBody User user) {
        log.info("user emp: {}", user);
        UserLoginInfo userLoginInfo = userService.login(user);
        if (userLoginInfo == null) {
            return Result.error("登录失败,用户名或密码错误");
        }else{
            return Result.success(userLoginInfo);
        }
    }

    /**
     * 微信登录
     */
    @PostMapping("/wechatLogin")
    public Result wechatAuthorize(@RequestParam String code) {
        log.info("wechat code: {}", code);
        try {
            // 获取微信用户信息
            WxMaUserService wxService = wechatService.getWxMaService().getUserService();
            WxMaJscode2SessionResult session = wxService.getSessionInfo(code);
            log.info("session: {}", session);

            String openid = session.getOpenid();
            String nickname = "WeChatUser"; // 可通过前端传入或后续获取

            User loginInfo = new User();
            loginInfo.setOpenid(openid);
            loginInfo.setNickname(nickname);

            // 调用业务层进行登录处理
            WxLoginInfo result = userService.getWxLoginInfo(loginInfo);

            if (result == null) {
                return Result.error("未找到该微信用户");
            } else {
                return Result.success(result);
            }
        } catch (Exception e) {
            log.error("微信登录失败，code={}, error={}", code, e.getMessage(), e); // 记录异常堆栈
            return Result.error("微信登录失败：" + e.getMessage());
        }
    }





}
