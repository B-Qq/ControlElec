package com.example.controlelec.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.controlelec.comm.Config;
import com.example.controlelec.consumer.AndianStationfrontMonListener;
import com.example.controlelec.service.DeviceService;
import com.example.controlelec.util.MQUilts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : liuze
 * @create 2019/5/23 16:13
 */
@Configuration
public class MQConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQUilts.class);

    @Autowired
    DeviceService deviceService;

    /**
     * 消费者  连接配置
     *
     * @return
     */

    private Properties consumerBeanProperties() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, Config.getConstant("ONS.ConsumerId"));
        properties.setProperty(PropertyKeyConst.AccessKey, Config.getConstant("ONS.AccessKey"));
        properties.setProperty(PropertyKeyConst.SecretKey, Config.getConstant("ONS.SecretKey"));
        properties.setProperty(PropertyKeyConst.ONSAddr, Config.getConstant("ONS.ONSAddr"));
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, Config.getConstant("ONS.ConsumeThreadNums"));
        return properties;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean consumerBean() {
        // 订阅主题
        Subscription subscription_AndianStationfrontMonListener = new Subscription();
        String topic = Config.getConstant("MQ.SUB.Deal_TOPIC").trim();
        String stationId = Config.getConstant("StationId").trim();
        String type = Config.getConstant("MQ.SUB.Deal_TAG").trim();
        subscription_AndianStationfrontMonListener.setTopic(topic);
        subscription_AndianStationfrontMonListener.setExpression(stationId + "@" + type);
        AndianStationfrontMonListener andianStationfrontMonListener = new AndianStationfrontMonListener();
        andianStationfrontMonListener.setDeviceService(deviceService);
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
        subscriptionTable.put(subscription_AndianStationfrontMonListener, andianStationfrontMonListener);
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(consumerBeanProperties());
        consumerBean.setSubscriptionTable(subscriptionTable);
        LOGGER.info("订阅 topic:{}, tag:{}", topic, stationId + "@" + type);
        return consumerBean;
    }
}
