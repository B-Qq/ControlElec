package com.example.controlelec.util;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.example.controlelec.comm.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : liuze
 * @create 2019/5/20 10:37
 */
public class MQUilts {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQUilts.class);

    /**
     * 消息发布者
     */
    private static Producer producer;

    /**
     * 生产者获取
     */
    public static Producer getProducer() {
        if (producer == null) {
            synchronized (MQUilts.class) {
                if (producer == null) {
                    Properties properties = new Properties();
                    properties.setProperty(PropertyKeyConst.ProducerId, Config.getConstant("ONS.ProducerId"));
                    properties.setProperty(PropertyKeyConst.AccessKey, Config.getConstant("ONS.AccessKey"));
                    properties.setProperty(PropertyKeyConst.SecretKey, Config.getConstant("ONS.SecretKey"));
                    properties.setProperty(PropertyKeyConst.ONSAddr, Config.getConstant("ONS.ONSAddr"));

                    LOGGER.info(" producer   init .... ");
                    producer = ONSFactory.createProducer(properties);
                    producer.start();
                }
            }
        }
        return producer;
    }

    /**
     * 发布 Topic 信息  加密内容
     *
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public static SendResult sendMsg(String topic, String tag, String msg) {
        Producer producer = null;
        SendResult sendResult = null;
        try {
            producer = getProducer();
            String msg_ = AESUtil.encryptData(msg);

            Message message = new Message(topic, tag, msg_.getBytes());
            sendResult = producer.send(message);
            LOGGER.info("发布topic :{} tag :{} msg:{}", topic, tag, msg);
        } catch (ONSClientException e) {
            LOGGER.error("Topic : {} ,tag :{} , Send failed!  Exception :{}", topic, tag, e.getMessage());
        } finally {
            return sendResult;
        }
    }


    /**
     * 发布 Topic 信息 不加密
     *
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public static SendResult sendMsgNoEncrypt(String topic, String tag, String msg) {
        Producer producer = null;
        SendResult sendResult = null;
        try {
            producer = getProducer();
            Message message = new Message(topic, tag, msg.getBytes());
            sendResult = producer.send(message);
            LOGGER.info("发布 MessageId:{} topic :{} tag :{} msg:{}", sendResult.getMessageId(), topic, tag, msg);
        } catch (ONSClientException e) {
            LOGGER.error("Topic : {} ,tag :{} , MessageId:{} Send failed!  Exception :{}", topic, tag, sendResult.getMessageId(), e.getMessage());
        } finally {
            return sendResult;
        }
    }


    /**
     * 通过不同的tag 从message中获取出功能表示
     *
     * @param message
     * @param tag_
     * @return
     * @author cd
     * @date 2019/9/6
     */
    public static String getMenuByMessage(Message message, String tag_) {
        String menu = null;
        menu = tag_.trim().split("@")[1];
        return menu;
    }

    /**
     * 通过不同的tag 从message中获取出站编号
     *
     * @param message
     * @param tag_
     * @return
     * @author cd
     * @date 2019/9/10
     */
    public static String getStationIdByMessage(Message message, String tag_) {
        String stationId;
        stationId = tag_.trim().split("@")[0];
        return stationId;
    }

}
