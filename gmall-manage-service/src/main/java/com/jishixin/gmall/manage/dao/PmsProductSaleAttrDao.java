package com.jishixin.gmall.manage.dao;/*
  User: 晨梦意志
  Date: 2019/9/11
  Time: 14:54
  Notes:
*/

import com.jishixin.gmall.pojo.PmsProductSaleAttr;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface PmsProductSaleAttrDao extends Mapper<PmsProductSaleAttr> {

    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(String productId, String skuId);
}
