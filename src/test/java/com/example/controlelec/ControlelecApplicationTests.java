package com.example.controlelec;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.controlelec.service.UserService;
import com.example.controlelec.bean.UserBean;

@SpringBootTest
class ControlelecApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        UserBean userBean = userService.loginIn("haihuide", "haihuide");
        logger.info("用户ID为:" + userBean.getUuid());
    }
}
