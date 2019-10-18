package com.jishixin.gmall.service;/*
  User: 晨梦意志
  Date: 2019/9/9
  Time: 9:11
  Notes:
*/

import com.jishixin.gmall.pojo.UmsMember;
import com.jishixin.gmall.pojo.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {


    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    UmsMember login(UmsMember umsMember);

    void addUserToken(String token, String memberId);

    UmsMember addOauthUser(UmsMember umsMember);

    UmsMember checkOauthUser(UmsMember umsMember1);

    UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId);
}
