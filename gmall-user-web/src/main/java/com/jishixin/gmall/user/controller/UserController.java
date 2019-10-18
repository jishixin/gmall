package com.jishixin.gmall.user.controller;

import com.jishixin.gmall.pojo.UmsMember;
import com.jishixin.gmall.pojo.UmsMemberReceiveAddress;
import com.jishixin.gmall.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/9
  Time: 9:08
  Notes:
*/
@Controller
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        List<UmsMember> userMembers =userService.getAllUser();
        return userMembers;
    }

    @RequestMapping("/getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses =userService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddresses;
    }

}
