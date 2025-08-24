package com.itheima.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itheima.utils.WxConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;


import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private WxConfig wxConfig;

    private final WxMaService wxMaService = new WxMaServiceImpl();
    private WxMaDefaultConfigImpl config;

    @PostConstruct
    public void init() {
        log.info("初始化微信服务配置: appId={}, appSecret length={}",
                wxConfig.getAppId(),
                wxConfig.getAppSecret() != null ? wxConfig.getAppSecret() : 0 );
        config = new WxMaDefaultConfigImpl();
        config.setAppid(wxConfig.getAppId());
        config.setSecret(wxConfig.getAppSecret());
        wxMaService.setWxMaConfig(config);
    }

    public WxMaService getWxMaService() {
        return this.wxMaService;
    }
}
