package com.jishixin.gmall.manage.dao;/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 11:04
  Notes:
*/

import com.jishixin.gmall.pojo.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PmsBaseAttrInfoDao extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIds") String valueIds);
}
