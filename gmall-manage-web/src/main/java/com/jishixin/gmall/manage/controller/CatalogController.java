package com.jishixin.gmall.manage.controller;

import com.jishixin.gmall.pojo.PmsBaseCatalog1;
import com.jishixin.gmall.pojo.PmsBaseCatalog2;
import com.jishixin.gmall.pojo.PmsBaseCatalog3;
import com.jishixin.gmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 9:34
  Notes:
*/
@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    private CatalogService catalogService;

    @RequestMapping("/getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s=catalogService.getCatalog1();
        return pmsBaseCatalog1s;
    }

    @RequestMapping("/getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam String catalog1Id){
        List<PmsBaseCatalog2> pmsBaseCatalog2s=catalogService.getCatalog2(catalog1Id);
        return pmsBaseCatalog2s;
    }

    @RequestMapping("/getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam String catalog2Id){
        List<PmsBaseCatalog3> pmsBaseCatalog3s=catalogService.getCatalog3(catalog2Id);
        return pmsBaseCatalog3s;
    }

}
