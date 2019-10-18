package com.jishixin.gmall.order.controller;

/*
  User: 晨梦意志
  Date: 2019/9/22
  Time: 17:18
  Notes:
*/

import com.jishixin.gmall.annotations.LoginRequired;
import com.jishixin.gmall.pojo.OmsCartItem;
import com.jishixin.gmall.pojo.OmsOrder;
import com.jishixin.gmall.pojo.OmsOrderItem;
import com.jishixin.gmall.pojo.UmsMemberReceiveAddress;
import com.jishixin.gmall.service.CartService;
import com.jishixin.gmall.service.OrderService;
import com.jishixin.gmall.service.SkuService;
import com.jishixin.gmall.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Reference
    private CartService cartService;

    @Reference
    private UserService userService;

    @Reference
    private OrderService orderService;

    @Reference
    private SkuService skuService;

    @RequestMapping("submitOrder")
    @LoginRequired(loginSuccess = true)
    public ModelAndView submitOrder(String tradeCode , String receiveAddressId, BigDecimal totalAmount , HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        String success = orderService.checkTradeCode(memberId,tradeCode);
        if (success.equals("success")){
            List<OmsOrderItem> omsOrderItems = new ArrayList<>();
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setAutoConfirmDay(7);
            omsOrder.setCreateTime(new Date());
            omsOrder.setDiscountAmount(null);
            omsOrder.setMemberId(memberId);
            omsOrder.setMemberUsername(nickname);
            omsOrder.setNote("快速发货");
            String outTradeNo = "gmall";
            outTradeNo += System.currentTimeMillis();
            outTradeNo += new SimpleDateFormat("YYYYMMDDHHmmss").format(new Date());
            omsOrder.setOrderSn(outTradeNo);
            omsOrder.setPayAmount(totalAmount);
            omsOrder.setOrderType(1);
            UmsMemberReceiveAddress umsMemberReceiveAddress=userService.getReceiveAddressById(receiveAddressId);
            omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
            omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
            omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());
            Calendar calendar =Calendar.getInstance();
            calendar.add(Calendar.DATE,1);
            omsOrder.setReceiveTime(calendar.getTime());
            omsOrder.setSourceType(0);
            omsOrder.setStatus("0");
            omsOrder.setTotalAmount(totalAmount);
            List<OmsCartItem> omsCartItems = cartService.cartList(memberId);
            for (OmsCartItem omsCartItem : omsCartItems) {
                if (omsCartItem.getIsChecked().equals("1")){
                    OmsOrderItem omsOrderItem = new OmsOrderItem();
                    boolean b = skuService.checkPrice(omsCartItem.getProductSkuId(),omsCartItem.getPrice());
                    if (b==false){
                        return new ModelAndView("tradeFail");
                    }
                    omsOrderItem.setProductPic(omsCartItem.getProductPic());
                    omsOrderItem.setProductName(omsCartItem.getProductName());
                    omsOrderItem.setOrderSn(outTradeNo);
                    omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
                    omsOrderItem.setProductPrice(omsCartItem.getPrice());
                    omsOrderItem.setRealAmount(omsCartItem.getTotalPrice());
                    omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                    omsOrderItem.setProductSkuCode("111111111");
                    omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                    omsOrderItem.setProductId(omsCartItem.getProductId());
                    omsOrderItem.setProductSn("仓库对应的商品编号");
                    omsOrderItems.add(omsOrderItem);
                }
            }
            omsOrder.setOmsOrderItems(omsOrderItems);
            orderService.saveOrder(omsOrder);
            ModelAndView modelAndView = new ModelAndView("redirect:http://127.0.0.1:8088/index");
            modelAndView.addObject("outTradeNo",outTradeNo);
            modelAndView.addObject("totalAmount",totalAmount);
            return modelAndView;
        }
        return new ModelAndView("tradeFail");
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");

        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal totalPrice = omsCartItem.getTotalPrice();

            if(omsCartItem.getIsChecked().equals("1")){
                totalAmount = totalAmount.add(totalPrice);
            }
        }

        return totalAmount;
    }

    @RequestMapping("toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String memberId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");
        List<OmsCartItem> omsCartItems = cartService.cartList(memberId);
        List<UmsMemberReceiveAddress> userAddressList = userService.getReceiveAddressByMemberId(memberId);
        List<OmsOrderItem> omsOrderItems = new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIsChecked().equals("1")){
                OmsOrderItem omsOrderItem = new OmsOrderItem();
                omsOrderItem.setProductName(omsCartItem.getProductName());
                omsOrderItem.setProductPic(omsCartItem.getProductPic());
                omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                omsOrderItems.add(omsOrderItem);
            }
        }
        String tradeCode = orderService.getTradeCode(memberId);
        modelMap.put("omsOrderItems",omsOrderItems);
        modelMap.put("userAddressList",userAddressList);
        modelMap.put("totalAmount",getTotalAmount(omsCartItems));
        modelMap.put("tradeCode",tradeCode);
        return "trade";
    }

}
