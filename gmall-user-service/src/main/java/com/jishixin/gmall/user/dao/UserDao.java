package com.jishixin.gmall.user.dao;/*
  User: 晨梦意志
  Date: 2019/9/9
  Time: 9:14
  Notes:
*/

import com.jishixin.gmall.pojo.UmsMember;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<UmsMember>{

    @Select("select * from ums_member")
    List<UmsMember> selectAllUser();
}
