package com.example.controlelec.util;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.powerpeak.comm.Config;
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
    public static Producer getProducer(){
        if (producer == null){
            synchronized (MQUilts.class){
                if (producer == null){
                    Properties properties = new Properties();
                    properties.setProperty(PropertyKeyConst.ProducerId, Config.getConstant("ONS.ProducerId"));
                    properties.setProperty(PropertyKeyConst.AccessKey,Config.getConstant("ONS.AccessKey"));
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
     * 消费者获取
     */
    public static Consumer getConsumer(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId,Config.getConstant("ONS.ConsumerId"));
        properties.setProperty(PropertyKeyConst.AccessKey,Config.getConstant("ONS.AccessKey"));
        properties.setProperty(PropertyKeyConst.SecretKey, Config.getConstant("ONS.SecretKey"));
        properties.setProperty(PropertyKeyConst.ONSAddr, Config.getConstant("ONS.ONSAddr"));
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, Config.getConstant("ONS.ConsumeThreadNums"));
        Consumer consumer = ONSFactory.createConsumer(properties);
        return consumer;
    }

    /**
     * 发布 Topic 信息  加密内容
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public static SendResult sendMsg(String topic,String tag,String msg){
        Producer producer = null;
        SendResult sendResult = null;
        try {
            producer = getProducer();
            String msg_ = AESUtil.encryptData(msg);

            Message message = new Message(topic, tag, msg_.getBytes());
            sendResult = producer.send(message);
            LOGGER.info("发布topic :{} tag :{} msg:{}",topic,tag,msg);
        } catch (ONSClientException e) {
            LOGGER.error("Topic : {} ,tag :{} , Send failed!  Exception :{}",topic,tag,e.getMessage());
        }  finally {
            return sendResult;
        }
    }


    /**
     * 发布 Topic 信息  加密内容(由RocketMQ 经MQTT 到站级)
     * @param topic
     * @param tag 由站编号+"@"+功能标识组成  例如: "42409000001@80"
     * @param msg 业务消息内容
     * @return
     */
    public static SendResult sendMqttMsg(String topic,String tag,String msg){
        Producer producer = null;
        SendResult sendResult = null;
        try {
            producer = getProducer();
            String msg_ = AESUtil.encryptData(msg);
            Message message = new Message(topic, tag, msg_.getBytes());
            message.putUserProperties(PropertyKeyConst.MqttSecondTopic, "/" + tag.replace("@","/"));
            message.putUserProperties(PropertyKeyConst.MqttQOS,"1");

            sendResult = producer.send(message);
            LOGGER.info("发布经mqtt的topic :{} tag :{} msg:{}",topic,tag,msg);
        } catch (ONSClientException e) {
            LOGGER.error("Topic : {} ,tag :{} , Send failed!  Exception :{}",topic,tag,e.getMessage());
        }  finally {
            return sendResult;
        }
    }


    /**
     * 发布 Topic 信息 不加密
     * @param topic
     * @param tag
     * @param msg
     * @return
     */
    public static SendResult sendMsgNoEncrypt(String topic,String tag,String msg){
        Producer producer = null;
        SendResult sendResult = null;
        try {
            producer = getProducer();
            Message message = new Message(topic, tag, msg.getBytes());
            sendResult = producer.send(message);
            LOGGER.info("发布 MessageId:{} topic :{} tag :{} msg:{}",sendResult.getMessageId(),topic,tag,msg);
        } catch (ONSClientException e) {
            LOGGER.error("Topic : {} ,tag :{} , MessageId:{} Send failed!  Exception :{}",topic,tag,sendResult.getMessageId(),e.getMessage());
        }  finally {
            return sendResult;
        }
    }

    /**
     * 获取 业务消息 tag
     * @param suffixStr 业务功能标识
     * @return
     */
//    public static String getBusinessMessageListenerTopic(String suffixStr){
//        String stationIDs =  Config.getConstant("StationIDs");
//        String[] stationIDsArrar = stationIDs.split(",");
//        //String result = "";
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < stationIDsArrar.length; i++) {
//            if (i == (stationIDsArrar.length - 1)){
//                result.append(stationIDsArrar[i]);
//                result.append("@");
//                result.append(suffixStr);
//                //result += stationIDsArrar[i] + "@" + suffixStr;
//            }else {
//                result.append(stationIDsArrar[i]);
//                result.append("@");
//                result.append(suffixStr);
//                result.append(" || ");
//                //result += stationIDsArrar[i] + "@" + suffixStr + " || ";
//            }
//        }
//        return result.toString();
//    }

    /**
     * 获取 业务消息 tag
     * @param suffixStr 业务功能标识
     * @return
     */
//    public static String getMessageListenerExpression(String... suffixStr){
//        //String result = "";
//        StringBuilder result = new StringBuilder();
//        for (int i =0 ; i < suffixStr.length; i++){
//            if (i == (suffixStr.length - 1)){
//                String topic = getBusinessMessageListenerTopic(suffixStr[i]);
//                result.append(topic);
//                //result += getBusinessMessageListenerTopic(suffixStr[i]);
//            }else {
//                String strTopic = getBusinessMessageListenerTopic(suffixStr[i]);
//                //result += getBusinessMessageListenerTopic(suffixStr[i]) + " || ";
//                result.append(strTopic);
//                result.append(" || ");
//            }
//        }
//        return result.toString();
//    }

    /**
     *   获取mqtt 的tag
     * @author cd
     * @date 2019/9/5
     * @param mqttSecondTopic
     * @return
     */
    public static String getMqttTag(String mqttSecondTopic){
        String tag = mqttSecondTopic.substring(mqttSecondTopic.lastIndexOf('/',mqttSecondTopic.lastIndexOf('/'))+1);
        return tag;
    }



    /**
     *   获取mqtt 的 StationId
     * @author cd
     * @date 2019/9/10
     * @param mqttSecondTopic
     * @return
     */
    public static String getMqttStationId(String mqttSecondTopic){
        String stationId = mqttSecondTopic.split("/")[1];
        return stationId;
    }


    /**
     *   通过不同的tag 从message中获取出功能表示
     * @author cd
     * @date 2019/9/6
     * @param message
     * @param tag_
     * @return
     */
    public static String getMenuByMessage(Message message, String tag_) {
        String menu = null;
        String mqttSecondTopic = null;
        if (Config.getConstant("MQTT.STATION.TAG").trim().equals(tag_)){
            mqttSecondTopic = message.getUserProperties("mqttSecondTopic");
            menu = MQUilts.getMqttTag(mqttSecondTopic);
        } else {
            menu = tag_.trim().split("@")[1];
        }
        return menu;
    }

    /**
     *   获取mqtt消息的mqttSecondTopic 信息
     * @author cd
     * @date 2019/9/16
     * @param message
     * @param tag_
     * @return
     */
    public static String getMqttSecondTopic(Message message,String tag_){
        String mqttSecondTopic = null;
        if (Config.getConstant("MQTT.STATION.TAG").trim().equals(tag_)){
            mqttSecondTopic = message.getUserProperties("mqttSecondTopic");
        } else {
            mqttSecondTopic = tag_.trim();
        }
        return mqttSecondTopic;
    }

    /**
     *   通过不同的tag 从message中获取出站编号
     * @author cd
     * @date 2019/9/10
     * @param message
     * @param tag_
     * @return
     */
    public static String getStationIdByMessage(Message message, String tag_){
        String stationId = null;
        String mqttSecondTopic = null;
        if (Config.getConstant("MQTT.STATION.TAG").trim().equals(tag_)){
            mqttSecondTopic = message.getUserProperties("mqttSecondTopic");
            stationId = MQUilts.getMqttStationId(mqttSecondTopic);
        } else {
            stationId = tag_.trim().split("@")[0];
        }
        return stationId;
    }




}
