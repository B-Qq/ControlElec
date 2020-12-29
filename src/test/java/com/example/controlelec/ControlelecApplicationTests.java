package com.example.controlelec;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.controlelec.service.UserService;
import com.example.controlelec.bean.UserBean;
import com.example.controlelec.mapper.DeviceOnlineWarnMapper;
import com.example.controlelec.bean.DeviceOnlineWarnBean;

import java.util.List;

@SpringBootTest
class ControlelecApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    DeviceOnlineWarnMapper deviceOnlineWarnMapper;

    @Test
    void contextLoads() {
        UserBean userBean = userService.loginIn("haihuide", "haihuide");
        logger.info("用户ID为:" + userBean.getUuid());
//        List<DeviceOnlineWarnBean> deviceOnlineWarnBean = deviceOnlineWarnMapper.getOnlineWarn("ef9dbbc9-40f4-11eb-bb39-000c29089546");
    }
}
