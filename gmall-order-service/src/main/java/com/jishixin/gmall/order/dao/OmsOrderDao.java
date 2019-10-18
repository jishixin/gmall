package com.jishixin.gmall.order.dao;
/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 12:27
  Notes:
*/

import com.jishixin.gmall.pojo.OmsOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OmsOrderDao extends Mapper<OmsOrder> {
}
