package com.jishixin.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.jishixin.gmall.pojo.UmsMember;
import com.jishixin.gmall.service.UserService;
import com.jishixin.gmall.util.HttpclientUtil;
import com.jishixin.gmall.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
  User: 晨梦意志
  Date: 2019/9/20
  Time: 15:36
  Notes:
*/
@Controller
public class PassportController {

    @Reference
    private UserService userService;

    @RequestMapping("/vlogin")
    public String vlogin(String code,HttpServletRequest request){
        // 授权码换取access_token
        // 换取access_token
        // client_secret=2f034023eeca3a5e3a5e2991e9c11c49
        // client_id=2777976327
        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("client_id","2777976327");
        paramMap.put("client_secret","2f034023eeca3a5e3a5e2991e9c11c49");
        paramMap.put("grant_type","authorization_code");
        paramMap.put("redirect_uri","http://127.0.0.1:8085/vlogin");
        paramMap.put("code",code);// 授权有效期内可以使用，没新生成一次授权码，说明用户对第三方数据进行重启授权，之前的access_token和授权码全部过期
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

        Map<String,Object> access_map = JSON.parseObject(access_token_json,Map.class);

        String uid = (String) access_map.get("uid");
        String access_token = (String) access_map.get("access_token");

        String show_user_url = "https://api.weibo.com/2/users/show.json?access_token="+access_token+"&uid="+uid;
        String user_json = HttpclientUtil.doGet(show_user_url);
        Map<String,Object> user_map = JSON.parseObject(user_json, Map.class);

        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType("2");
        umsMember.setSourceUid(String.valueOf(user_map.get("id")));
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setCity((String) user_map.get("location"));
        umsMember.setGender(user_map.get("gender") == "f" ? "2" : "1");
        umsMember.setNickname((String) user_map.get("screen_name"));
        UmsMember umsMember1 =new UmsMember();
        umsMember1.setSourceUid(umsMember.getSourceUid());
        UmsMember umsMemberCheck=userService.checkOauthUser(umsMember1);
        if (umsMemberCheck==null){
            umsMember=userService.addOauthUser(umsMember);
        }else {
            umsMember=umsMemberCheck;
        }
        String token = "";
        String memberId= umsMember.getId();
        String nicknaem = umsMember.getNickname();
        Map<String,Object> map = new HashMap<>();
        map.put("memberId",memberId);
        map.put("nickname",nicknaem);
        String ip = request.getHeader("x-forwarded-for");
        if(StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();
            if (StringUtils.isBlank(ip)){
                ip="127.0.0.1";
            }
        }
        token = JwtUtil.encode("2019gmall0105", map, ip);
        userService.addUserToken(token,memberId);
        return "redirect:http://127.0.0.1:8083/index?token="+token;
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verify(String token, String currentIp){
        Map<String,String> map = new HashMap<>();
        Map<String, Object> decode = JwtUtil.decode(token, "2019gmall0105", currentIp);
        if (decode!=null){
            map.put("status","success");
            map.put("memberId",(String) decode.get("memberId"));
            map.put("nickname",(String) decode.get("nickname"));
        }else {
            map.put("status","fail");
        }

        return JSON.toJSONString(map);
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request){
        String token = "";
        UmsMember umsMemberLogin =userService.login(umsMember);
        if (umsMemberLogin!=null){
            Map<String,Object> map = new HashMap<>();
            String memberId = umsMemberLogin.getId();
            String nicknaem = umsMemberLogin.getUsername();
            map.put("memberId",memberId);
            map.put("nickname",nicknaem);
            String ip = request.getHeader("x-forwarded-for");
            if(StringUtils.isBlank(ip)){
                ip = request.getRemoteAddr();
                if (StringUtils.isBlank(ip)){
                    ip="127.0.0.1";
                }
            }
            token = JwtUtil.encode("2019gmall0105", map, ip);
            userService.addUserToken(token,memberId);
        }else {
            token = "fail";
        }
        return token;
    }

    @RequestMapping("/index")
    public String index(String ReturnUrl, Model model){
        model.addAttribute("ReturnUrl",ReturnUrl);
        return "index";
    }

}
