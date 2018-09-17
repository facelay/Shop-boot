package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String OPENID = "gimiopenid";

    private final String ORDERID = "91253386578847814867";

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("颜福兴");
        orderDTO.setBuyerAddress("北京朝阳区");
        orderDTO.setBuyerOpenid(OPENID);
        orderDTO.setBuyerPhone("18725726147");

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(1);
        orderDetails.add(orderDetail);

//        OrderDetail od2 = new OrderDetail();
//        od2.setProductId("123457");
//        od2.setProductQuantity(2);
//        orderDetails.add(od2);

        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO resut = orderService.findOne(ORDERID);
        log.info("【查询单个订单】result={}", resut);
        Assert.assertEquals(ORDERID, resut.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderDTO> orderDTOPage = orderService.findList(OPENID, pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {

        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void list() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(OPENID, pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

}