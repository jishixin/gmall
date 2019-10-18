package com.jishixin.gmall.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jishixin.gmall.annotations.LoginRequired;
import com.jishixin.gmall.config.AlipayConfig;
import com.jishixin.gmall.pojo.OmsOrder;
import com.jishixin.gmall.pojo.PaymentInfo;
import com.jishixin.gmall.service.OrderService;
import com.jishixin.gmall.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 13:36
  Notes:
*/
@Controller
public class PaymentController {

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    PaymentService paymentService;

    @Reference
    OrderService orderService;

    @RequestMapping("alipay/callback/return")
    @LoginRequired(loginSuccess = true)
    public String aliPayCallBackReturn(String outTradeNo, String totalAmount, HttpServletRequest request, ModelMap modelMap){
        String sign = request.getParameter("sign");
        String trade_no = request.getParameter("trade_no");
        String out_trade_no = request.getParameter("out_trade_no");
        String trade_status = request.getParameter("trade_status");
        String total_amount = request.getParameter("total_amount");
        String subject = request.getParameter("subject");
        String call_back_content = request.getQueryString();
        if (StringUtils.isNotBlank(sign)){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOrderSn(out_trade_no);
            paymentInfo.setPaymentStatus("已支付");
            paymentInfo.setAlipayTradeNo(trade_no);
            paymentInfo.setCallbackContent(call_back_content);
            paymentInfo.setCallbackTime(new Date());
            paymentService.updatePayment(paymentInfo);
        }
        return "finish";
    }

    @RequestMapping("wx/submit")
    @LoginRequired(loginSuccess = true)
    public String wx(String outTradeNo, String totalAmount, HttpServletRequest request, ModelMap modelMap){
        return null;
    }

    @RequestMapping("alipay/submit")
    @LoginRequired(loginSuccess = true)
    @ResponseBody
    public String alipay(String outTradeNo, BigDecimal totalAmount, HttpServletRequest request, ModelMap modelMap){
        String form = null;
        try {
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
            alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
            Map<String,Object> map = new HashMap<>();
            map.put("out_trade_no",outTradeNo);
            map.put("product_code","FAST_INSTANT_TRADE_PAY");
            map.put("total_amount",0.01);
            map.put("subject","晨梦意志");
            String param = JSON.toJSONString(map);
            alipayRequest.setBizContent(param);
            form=alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        OmsOrder omsOrder = orderService.getOrderByOutTradeNo(outTradeNo);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(omsOrder.getId());
        paymentInfo.setOrderSn(outTradeNo);
        paymentInfo.setPaymentStatus("未付款");
        paymentInfo.setSubject("晨梦意志");
        paymentInfo.setTotalAmount(totalAmount);
        paymentService.savePaymentInfo(paymentInfo);
        //发送延迟消息队列
        paymentService.sendDelayPaymentResultCheckQueue(outTradeNo,5);
        return form;
    }

    @RequestMapping("/index")
    @LoginRequired(loginSuccess = true)
    public String index(String outTradeNo, String totalAmount, HttpServletRequest request, ModelMap modelMap){
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        modelMap.put("nickname",nickname);
        modelMap.put("outTradeNo",outTradeNo);
        modelMap.put("totalAmount",totalAmount);
        return "index";
    }

}
