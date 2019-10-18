package com.jishixin.gmall.manage.service.impl;

import com.jishixin.gmall.manage.dao.PmsBaseCatalog1Dao;
import com.jishixin.gmall.manage.dao.PmsBaseCatalog2Dao;
import com.jishixin.gmall.manage.dao.PmsBaseCatalog3Dao;
import com.jishixin.gmall.pojo.PmsBaseCatalog1;
import com.jishixin.gmall.pojo.PmsBaseCatalog2;
import com.jishixin.gmall.pojo.PmsBaseCatalog3;
import com.jishixin.gmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 9:52
  Notes:
*/
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private PmsBaseCatalog1Dao pmsBaseCatalog1Dao;

    @Autowired
    private PmsBaseCatalog2Dao pmsBaseCatalog2Dao;

    @Autowired
    private PmsBaseCatalog3Dao pmsBaseCatalog3Dao;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Dao.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2=new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        return pmsBaseCatalog2Dao.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3=new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        return pmsBaseCatalog3Dao.select(pmsBaseCatalog3);
    }
}
