package com.jishixin.gmall.service;

import com.jishixin.gmall.pojo.PmsBaseCatalog1;
import com.jishixin.gmall.pojo.PmsBaseCatalog2;
import com.jishixin.gmall.pojo.PmsBaseCatalog3;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 9:50
  Notes:
*/
public interface CatalogService {

    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
