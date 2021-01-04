package com.example.controlelec.consumer;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import com.example.controlelec.bean.DeviceBean;
import com.example.controlelec.service.DeviceService;
import com.example.controlelec.util.MQUilts;
import com.example.controlelec.util.StringUtils;
import com.example.controlelec.util.AESUtil;
import com.example.controlelec.util.ThreadPoolUtils;
import com.example.controlelec.service.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class AndianStationfrontMonListener implements MessageListener {

    /**
     * ANDIAN_STATIONFRONT_MON
     * 停电原因	25
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(AndianStationfrontMonListener.class);
    private DeviceService deviceService;

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
        String topic_;
        String tag_;
        String msg_;
        String menu = null;
        try {
            topic_ = message.getTopic();
            tag_ = message.getTag();
            msg_ = new String(message.getBody());
            menu = MQUilts.getMenuByMessage(message, tag_);
            if (!StringUtils.checkIsNotEmpty(topic_, tag_, msg_, menu)) {
                LOGGER.error("### AndianStationRpsListener    MQ 消息数据 NULL");
                return Action.CommitMessage;
            }

            if ("25".equals(menu)) {
                LOGGER.info("### 接收到异常断电： topic: {}, tag: {} msg: {} ", topic_, tag_, msg_);
                msg_ = AESUtil.decryptData(msg_);
                // 前置推送推送异常停电原因
                businessmessagelistener_25(topic_, tag_, msg_, menu);
            } else {
                return Action.CommitMessage;
            }
            return Action.CommitMessage;
        } catch (java.lang.reflect.UndeclaredThrowableException e1) {
            LOGGER.error("HSF 离线  Exception :{}", e1.getMessage());
            return Action.CommitMessage;
        } catch (Exception e) {
            LOGGER.error("CusmerMQ_{}  Exception :{}", menu, e.getMessage());
            return Action.CommitMessage;
        } finally {
            return Action.CommitMessage;
        }
    }

    /**
     * 异常停电原因	25
     */
    private void businessmessagelistener_25(String topic_, String tag_, String msg_, String menu) {
        ThreadPoolExecutor executor = ThreadPoolUtils.getThreadPoolExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonObject = JSONObject.parseObject(msg_);
                    String stakeNo = StringUtils.getString(jsonObject, "STAKENO");
                    String chargePort = StringUtils.getString(jsonObject, "CHARGEPORT");
                    String stopReason = StringUtils.getString(jsonObject, "STOP_ELEC_REASON");

                    DeviceBean deviceBean = deviceService.getDeviceInfo(stakeNo);
                    String pushString = "设备:" + deviceBean.getStakeName() + " 端口:" + chargePort + " " + stopReason;

                    LOGGER.info("### 推送异常停电原因给页面 ：  Push {}", pushString);
                    //发给指定用户
                    WebSocketServer.sendInfo(deviceBean.getUuid(), pushString);
                    //发给root用户
                    WebSocketServer.sendInfoToRoot(pushString);
                } catch (Exception e) {
                    LOGGER.error("异常停电处理失败 Exception :{} , msg: {}", e.getMessage(), msg_);
                }

            }
        });
    }
}
