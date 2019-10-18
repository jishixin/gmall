package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/18
  Time: 16:55
  Notes:
*/

import com.jishixin.gmall.pojo.PmsSearchParam;
import com.jishixin.gmall.pojo.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
