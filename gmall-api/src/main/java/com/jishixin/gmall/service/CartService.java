package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/19
  Time: 18:33
  Notes:
*/

import com.jishixin.gmall.pojo.OmsCartItem;

import java.util.List;

public interface CartService {
    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(String memberId);

    List<OmsCartItem> cartList(String userId);

    void checkCart(OmsCartItem omsCartItem);
}
