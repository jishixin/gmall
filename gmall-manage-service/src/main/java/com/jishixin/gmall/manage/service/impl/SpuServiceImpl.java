package com.jishixin.gmall.manage.service.impl;

import com.jishixin.gmall.manage.dao.*;
import com.jishixin.gmall.pojo.*;
import com.jishixin.gmall.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 14:09
  Notes:
*/
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoDao pmsProductInfoDao;

    @Autowired
    private PmsProductImageDao pmsProductImageDao;

    @Autowired
    private PmsProductSaleAttrDao pmsProductSaleAttrDao;

    @Autowired
    private PmsProductSaleAttrValueDao pmsProductSaleAttrValueDao;

    @Autowired
    private PmsSkuSaleAttrValueDao pmsSkuSaleAttrValueDao;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo=new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return pmsProductInfoDao.select(pmsProductInfo);
    }

    @Override
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        // 保存商品信息
        pmsProductInfoDao.insertSelective(pmsProductInfo);

        // 生成商品主键
        String productId = pmsProductInfo.getId();

        // 保存商品图片信息
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(productId);
            pmsProductImageDao.insertSelective(pmsProductImage);
        }

        // 保存销售属性信息
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(productId);
            pmsProductSaleAttrDao.insertSelective(pmsProductSaleAttr);

            // 保存销售属性值
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(productId);
                pmsProductSaleAttrValueDao.insertSelective(pmsProductSaleAttrValue);
            }
        }
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr =new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrDao.select(pmsProductSaleAttr);
        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue =new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> select = pmsProductSaleAttrValueDao.select(pmsProductSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(select);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage pmsProductImage =new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        return pmsProductImageDao.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId) {
       /* PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(productId);
        List<PmsProductSaleAttr> select = pmsProductSaleAttrDao.select(pmsProductSaleAttr);
        for (PmsProductSaleAttr productSaleAttr : select) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            pmsProductSaleAttrValue.setProductId(productSaleAttr.getProductId());
            List<PmsProductSaleAttrValue> select1 = pmsProductSaleAttrValueDao.select(pmsProductSaleAttrValue);
            for (PmsProductSaleAttrValue productSaleAttrValue : select1) {
                PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
                pmsSkuSaleAttrValue.setSaleAttrValueId(productSaleAttrValue.getId());
                pmsSkuSaleAttrValue.setSkuId(skuId);
                pmsSkuSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
                pmsSkuSaleAttrValue=pmsSkuSaleAttrValueDao.selectOne(pmsSkuSaleAttrValue);
                if (pmsSkuSaleAttrValue!=null)
                    productSaleAttrValue.setIsChecked("1");
                else productSaleAttrValue.setIsChecked("0");
            }
            productSaleAttr.setSpuSaleAttrValueList(select1);
        }*/
        List<PmsProductSaleAttr> pmsProductSaleAttrList=pmsProductSaleAttrDao.selectSpuSaleAttrListCheckBySku(productId,skuId);
        return pmsProductSaleAttrList;
    }
}
