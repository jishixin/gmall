package com.jishixin.gmall.order.dao;
/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 12:33
  Notes:
*/

import com.jishixin.gmall.pojo.OmsOrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OmsOrderItemDao extends Mapper<OmsOrderItem> {
}
