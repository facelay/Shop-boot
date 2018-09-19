package com.imooc.sell.controller;

import cn.hutool.http.HttpResponse;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping("/user")
public class SellerUserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SellerInfoService sellerInfoService;

    @PostMapping("/login")
    public ModelAndView login(HttpServletResponse response,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password) {
        Map<String, Object> map = new HashMap<>();
        SellerInfo sellerInfo = sellerInfoService.findByUsernameAndPassword(username, password);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        //设置token至redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), sellerInfo.getSellerId(), RedisConstant.EXPIRE, TimeUnit.SECONDS);

        return new ModelAndView("", map);

    }


}
