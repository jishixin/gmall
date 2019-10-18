package com.jishixin.gmall.cart.dao;
/*
  User: 晨梦意志
  Date: 2019/9/20
  Time: 11:04
  Notes:
*/

import com.jishixin.gmall.pojo.OmsCartItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OmsCartItemDao extends Mapper<OmsCartItem> {
}
