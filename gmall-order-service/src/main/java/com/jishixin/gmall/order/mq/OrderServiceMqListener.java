package com.jishixin.gmall.order.mq;

import com.jishixin.gmall.pojo.OmsOrder;
import com.jishixin.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/*
  User: 晨梦意志
  Date: 2019/9/24
  Time: 8:54
  Notes:
*/
@Component
public class OrderServiceMqListener {

    @Autowired
    private OrderService orderService;

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE",containerFactory = "jmsQueueListener")
    public void consumePaymentResult(MapMessage mapMessage) throws JMSException {
        String out_trade_no = mapMessage.getString("out_trade_no");
        System.out.println(out_trade_no);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(out_trade_no);
        orderService.updateOrder(omsOrder);
        System.out.println("11111111111111111111");
    }

}
