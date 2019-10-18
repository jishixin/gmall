package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/11
  Time: 16:20
  Notes:
*/

import com.jishixin.gmall.pojo.PmsSkuInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);

    PmsSkuInfo getSkuByIdFromDb(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    List<PmsSkuInfo> getAllSku(String catalogId) throws InvocationTargetException, IllegalAccessException, IOException;

    boolean checkPrice(String productId, BigDecimal productPrice);

}
