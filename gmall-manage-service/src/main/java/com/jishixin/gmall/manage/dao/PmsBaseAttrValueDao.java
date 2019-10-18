package com.jishixin.gmall.manage.dao;/*
  User: 晨梦意志
  Date: 2019/9/10
  Time: 11:10
  Notes:
*/

import com.jishixin.gmall.pojo.PmsBaseAttrValue;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface PmsBaseAttrValueDao extends Mapper<PmsBaseAttrValue>, MySqlMapper<PmsBaseAttrValue> {
}
