package com.jishixin.gmall.manage.controller;

import com.jishixin.gmall.pojo.PmsBaseAttrInfo;
import com.jishixin.gmall.pojo.PmsBaseAttrValue;
import com.jishixin.gmall.pojo.PmsBaseSaleAttr;
import com.jishixin.gmall.service.AttrService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 10:59
  Notes:
*/
@Controller
@CrossOrigin
public class AttrController {

    @Reference
    private AttrService attrService;

    @RequestMapping("/attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam String catalog3Id){
        return  attrService.attrInfoList(catalog3Id);
    }

    @RequestMapping("/saveAttrInfo")
    @ResponseBody
    public String  saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        String status = attrService.saveAttrInfo(pmsBaseAttrInfo);
        return  status;
    }

    @RequestMapping("/getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue>  getAttrValueList(@RequestParam String attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues =attrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }

    @RequestMapping("/baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr>  baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs =attrService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

}
