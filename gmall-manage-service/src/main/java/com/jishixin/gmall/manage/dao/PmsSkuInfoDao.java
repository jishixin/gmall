package com.jishixin.gmall.manage.dao;

import com.jishixin.gmall.pojo.PmsSkuInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/11
  Time: 16:23
  Notes:
*/
@Repository
public interface PmsSkuInfoDao extends Mapper<PmsSkuInfo> {

    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);

}
