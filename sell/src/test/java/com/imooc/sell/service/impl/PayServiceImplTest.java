package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    private final String ORDERID = "91253386578847814867";

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {

        OrderDTO orderDTO = orderService.findOne(ORDERID);

        payService.create(orderDTO);
    }
}