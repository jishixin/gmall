package com.jishixin.gmall.payment.dao;
/*
  User: 晨梦意志
  Date: 2019/9/23
  Time: 16:30
  Notes:
*/

import com.jishixin.gmall.pojo.PaymentInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PaymentInfoDao extends Mapper<PaymentInfo> {
}
