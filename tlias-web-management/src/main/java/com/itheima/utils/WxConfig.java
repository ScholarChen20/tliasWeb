package com.itheima.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx")   //  读取配置文件中的 wx 开头的配置
@Data
public class WxConfig {
    private String appId;
    private String appSecret;
}
