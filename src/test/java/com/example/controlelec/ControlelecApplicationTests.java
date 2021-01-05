package com.example.controlelec;

import com.aliyun.openservices.ons.api.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.controlelec.service.UserService;
import com.example.controlelec.bean.UserBean;

import java.util.Properties;

@SpringBootTest
class ControlelecApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
//    UserService userService;

    @Test
    void contextLoads() {
//        UserBean userBean = userService.loginIn("haihuide", "haihuide");
//        logger.info("用户ID为:" + userBean.getUuid());
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_ADY_ZNRT_CLOUD_ELEC_CONTROL_TEST");
        properties.put(PropertyKeyConst.AccessKey, "4b6948da6a4644b1bab33aa96465caad");
        properties.put(PropertyKeyConst.SecretKey, "bcNcgvJrCAmOsD5VNMKERRl6iww=");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("ANDIAN_STATIONFRONT_MON", "*", (message, context) -> {
            System.out.println("Receive: " + message);
            return Action.CommitMessage;
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
