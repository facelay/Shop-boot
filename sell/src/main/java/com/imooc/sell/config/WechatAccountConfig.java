package com.imooc.sell.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    //微信公众号APPID
    private String mpAppId;

    //微信公众号APPSECRET
    private String mpAppSecret;

    //微信开放平台APPID
    private String openAppId;

    //微信开放平台APPSECRET
    private String openAppSecret;

    //商户号
    private String mchId;

    //商户密钥
    private String mchKey;

    //商户证书路径
    private String keyPath;

    //微信支付异步通知
    private String notiryUrl;
}
