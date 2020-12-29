package com.example.controlelec.controller;

import com.example.controlelec.bean.UserBean;
import com.example.controlelec.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 失败返回{'status':'0'}, 成功返回{'status':'1','uuid':'xx-xxx-xx-xx'}
     */
    @RequestMapping(path = "/logini", method = RequestMethod.POST)
    public Map<String, String> login(String userName, String password) {
        Map<String, String> mapRsp = new HashMap<String, String>(1);
        try {
            UserBean userBean = userService.loginIn(userName, password);
            if (userBean != null) {
                logger.info("用户登录, 用户名:" + userName + " 密码:" + password + " uuid:" + userBean.getUuid());
                mapRsp.put("status", "1");
                mapRsp.put("uuid", userBean.getUuid());
            } else {
                logger.warn("用户登录, 用户名或密码不正确");
                mapRsp.put("status", "0");
            }
            return mapRsp;
        } catch (Exception e) {
            logger.error("登录异常 " + e.getMessage());
            mapRsp.put("status", "0");
            return mapRsp;
        }
    }
}
