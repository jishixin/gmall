package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 14:07
  Notes:
*/

import com.jishixin.gmall.pojo.PmsProductImage;
import com.jishixin.gmall.pojo.PmsProductInfo;
import com.jishixin.gmall.pojo.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId);
}
