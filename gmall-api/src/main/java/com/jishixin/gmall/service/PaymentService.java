package com.jishixin.gmall.service;
/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 16:09
  Notes:
*/

import com.jishixin.gmall.pojo.PaymentInfo;

import java.util.Map;

public interface PaymentService {
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePayment(PaymentInfo paymentInfo);

    void sendDelayPaymentResultCheckQueue(String outTradeNo,int count);

    Map<String, Object> checkAlipayPayment(String out_trade_no);
}
