package com.jishixin.gmall.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jishixin.gmall.pojo.UmsMember;
import com.jishixin.gmall.pojo.UmsMemberReceiveAddress;
import com.jishixin.gmall.service.UserService;
import com.jishixin.gmall.user.dao.UmsMemberReceiveAddressDao;
import com.jishixin.gmall.user.dao.UserDao;
import com.jishixin.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

/*
  User: 晨梦意志
  Date: 2019/9/9
  Time: 9:11
  Notes:
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UmsMemberReceiveAddressDao umsMemberReceiveAddressDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<UmsMember> getAllUser() {
        return userDao.selectAll();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        return umsMemberReceiveAddressDao.select(umsMemberReceiveAddress);
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try{
            jedis = redisUtil.getJedis();
            if (jedis!=null){
                String umsMemberStr = jedis.get("user:" + umsMember.getPassword()+umsMember.getUsername() + ":info");
                if (StringUtils.isNotBlank(umsMemberStr)){
                    //登录成功
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return umsMemberFromCache;
                }
            }
            UmsMember umsMemberFromDb=loginFromDb(umsMember);
            if (umsMemberFromDb!=null){
                jedis.setex("user:" + umsMember.getPassword()+umsMember.getUsername() + ":info",60*60*24,JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = redisUtil.getJedis();
        jedis.setex("user:" + memberId + ":token", 60*60*2 , token);
        jedis.close();
    }

    @Override
    public UmsMember addOauthUser(UmsMember umsMember) {
        userDao.insertSelective(umsMember);
        return umsMember;
    }

    @Override
    public UmsMember checkOauthUser(UmsMember umsMember1) {
        return userDao.selectOne(umsMember1);
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(receiveAddressId);
        return umsMemberReceiveAddressDao.selectOne(umsMemberReceiveAddress);
    }

    private UmsMember loginFromDb(UmsMember umsMember) {
        List<UmsMember> umsMembers = userDao.select(umsMember);
        if (umsMembers!=null){
            return umsMembers.get(0);
        }
        return null;
    }
}
