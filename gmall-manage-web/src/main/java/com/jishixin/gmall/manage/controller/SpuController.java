package com.jishixin.gmall.manage.controller;

import com.jishixin.gmall.manage.util.PmsUploadUtil;
import com.jishixin.gmall.pojo.PmsBaseSaleAttr;
import com.jishixin.gmall.pojo.PmsProductImage;
import com.jishixin.gmall.pojo.PmsProductInfo;
import com.jishixin.gmall.pojo.PmsProductSaleAttr;
import com.jishixin.gmall.service.SpuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 14:04
  Notes:
*/
@Controller
@CrossOrigin
public class SpuController {

    @Reference
    private SpuService spuService;

    @RequestMapping("/spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(@RequestParam String catalog3Id) {
        return spuService.spuList(catalog3Id);
    }

    @RequestMapping("/saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        System.out.println(pmsProductInfo);
        return "success";
    }

    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(name = "file") MultipartFile multipartFile) {
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(imgUrl);
        return imgUrl;
    }

    @RequestMapping("/spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(@RequestParam String spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    @RequestMapping("/spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(@RequestParam String spuId) {
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }
}