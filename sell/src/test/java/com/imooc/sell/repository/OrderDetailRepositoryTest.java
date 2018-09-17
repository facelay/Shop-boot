package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("22222");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("http://******.jpg");
        orderDetail.setProductId("12344");
        orderDetail.setProductName("爆爆冰");
        orderDetail.setProductPrice(new BigDecimal(5));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> result = repository.findByOrderId("123456");
        Assert.assertNotEquals(0, result.size());
    }
}