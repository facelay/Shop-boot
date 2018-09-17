package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 微信支付统一下单
     *
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】payRequest={}", payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】payResponse={}", payResponse);
        return payResponse;
    }

    /**
     * 微信支付异步通知
     *
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {

        //1、签名校验
        //2、支付状态判断
        //3、支付金额判断
        //4、支付人判断（支付人 == 下单人）

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}", payResponse);

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            log.error("【微信支付】异步通知，订单不存在，orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付】异步通知，订单金额不一致，orderId={},微信通知金额={},系统订单金额={}",
                    orderDTO.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】refundRequest={}", refundRequest);

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】refundResponse={}", refundResponse);

        return refundResponse;
    }


}
