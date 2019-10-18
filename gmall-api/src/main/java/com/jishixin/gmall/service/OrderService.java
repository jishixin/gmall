package com.jishixin.gmall.service;
/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 9:41
  Notes:
*/

import com.jishixin.gmall.pojo.OmsOrder;

import java.math.BigDecimal;

public interface OrderService {
    String checkTradeCode(String memberId, String tradeCode);

    String getTradeCode(String memberId);

    void saveOrder(OmsOrder omsOrder);

    OmsOrder getOrderByOutTradeNo(String outTradeNo);

    void updateOrder(OmsOrder omsOrder);
}
