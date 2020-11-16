package com.fujin.bootdemo.web;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;


import com.fujin.bootdemo.dto.UserInfo;
import com.fujin.bootdemo.dto.WxLoginInfo;
import com.fujin.bootdemo.entity.User;
import com.fujin.bootdemo.service.UserService;
import com.fujin.bootdemo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import sun.util.calendar.LocalGregorianCalendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Hello {
    @Resource
    private UserService userService;

    @Autowired
    private WxMaService wxService;


    /**
    * @Description: 微信登录
    * @Param:
    * @return:
    */
    @PostMapping("/wx/login")
    public Object Hello(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }

        User user = userService.queryByOid(openId);

        System.out.println(user);

        if (user == null) {  // 新增用户
            user = new User();
            user.setUsername(userInfo.getNickName());
            user.setWxOpenid(openId);
            user.setGender(userInfo.getGender());
            user.setBirthday(LocalDate.now());
            user.setAvatar(userInfo.getAvatarUrl());
            user.setStatus((byte) 0);
            user.setLoginTime(LocalDateTime.now());
            user.setLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            user.setAvatar(userInfo.getAvatarUrl());
            userService.insertSelective(user);
        } else if (user.getPhone() == null){
            return ResponseUtil.unBandPhone();
        } else {
            user.setLoginTime(LocalDateTime.now());
            user.setLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        // String token = UserTokenManager.generateToken(user.getId());

        return user;
    }


    /**
    * @Description: 绑定手机号
    * @Param:
    * @return:
    */

    @PostMapping("/wx/bandPhone")
    public Object bandPhone(@RequestBody String body, HttpServletRequest request) {
        String phoneNumber = JacksonUtil.parseString(body, "phone");
        String openId = JacksonUtil.parseString(body, "openId");
        String sessionKey = JacksonUtil.parseString(body, "sessionKey");
        String userId = JacksonUtil.parseString(body, "id");
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.fail();
        }
        User user = userService.queryByOid(openId);
        if (user == null) {
            return ResponseUtil.fail();
        }

        assert userId != null;
        user.setId(Integer.valueOf(userId));
        user.setLoginTime(LocalDateTime.now());
        user.setLoginIp(IpUtil.getIpAddr(request));
        user.setSessionKey(sessionKey);
        user.setPhone(phoneNumber);


        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }
}


