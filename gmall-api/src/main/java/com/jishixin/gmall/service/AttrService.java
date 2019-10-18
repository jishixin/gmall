package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 11:02
  Notes:
*/

import com.jishixin.gmall.pojo.PmsBaseAttrInfo;
import com.jishixin.gmall.pojo.PmsBaseAttrValue;
import com.jishixin.gmall.pojo.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
