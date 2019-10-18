package com.jishixin.gmall.manage.service.impl;

import com.jishixin.gmall.manage.dao.PmsBaseAttrInfoDao;
import com.jishixin.gmall.manage.dao.PmsBaseAttrValueDao;
import com.jishixin.gmall.manage.dao.PmsBaseSaleAttrDao;
import com.jishixin.gmall.pojo.PmsBaseAttrInfo;
import com.jishixin.gmall.pojo.PmsBaseAttrValue;
import com.jishixin.gmall.pojo.PmsBaseSaleAttr;
import com.jishixin.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 11:03
  Notes:
*/
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private PmsBaseAttrInfoDao pmsBaseAttrInfoDao;

    @Autowired
    private PmsBaseAttrValueDao pmsBaseAttrValueDao;

    @Autowired
    private PmsBaseSaleAttrDao pmsBaseSaleAttrDao;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo=new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoDao.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos) {
            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue =new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            pmsBaseAttrValues=pmsBaseAttrValueDao.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        /*if (StringUtils.isEmpty(pmsBaseAttrInfo.getId())){
            pmsBaseAttrInfoDao.insert(pmsBaseAttrInfo);
            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            }
            pmsBaseAttrValueDao.insertList(pmsBaseAttrInfo.getAttrValueList());
        }else {
            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                if (StringUtils.isEmpty(pmsBaseAttrValue.getId())){
                    pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                    pmsBaseAttrValueDao.insert(pmsBaseAttrValue);
                }else {
                    pmsBaseAttrValueDao.updateByPrimaryKey(pmsBaseAttrValue);
                }
            }
        }*/
        if(StringUtils.isBlank(pmsBaseAttrInfo.getId())){
            pmsBaseAttrInfoDao.insertSelective(pmsBaseAttrInfo);
            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueDao.insertSelective(pmsBaseAttrValue);
            }
        }else {
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoDao.updateByExampleSelective(pmsBaseAttrInfo,example);
            List<PmsBaseAttrValue> pmsBaseAttrValues=pmsBaseAttrInfo.getAttrValueList();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueDao.delete(pmsBaseAttrValue);
            for (PmsBaseAttrValue AttrValue : pmsBaseAttrValues) {
                pmsBaseAttrValueDao.insertSelective(AttrValue);
            }
        }
        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueDao.select(pmsBaseAttrValue);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrDao.selectAll();
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet) {
        String valueIds = org.apache.commons.lang3.StringUtils.join(valueIdSet,",");
        List<PmsBaseAttrInfo> pmsBaseAttrInfos=pmsBaseAttrInfoDao.selectAttrValueListByValueId(valueIds);
        return pmsBaseAttrInfos;
    }
}
