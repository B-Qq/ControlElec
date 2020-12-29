package com.example.controlelec.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.controlelec.bean.DeviceSignalBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.controlelec.util.RedisUtil;
import com.example.controlelec.bean.DeviceMeterBean;
import com.example.controlelec.bean.DeviceElecBean;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeviceRealTimeMsgController {

    private final Logger logger = LoggerFactory.getLogger(DeviceRealTimeMsgController.class);
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(path = "/getMeter", method = RequestMethod.POST)
    public DeviceMeterBean getMeter(String stakeNo, String port) {
        String keyMeter = "AD:RTD:33:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keyMeter);
        if (object != null) {
            logger.info(object.toString());
        } else {
            logger.error("未获取到遥测数据 " + stakeNo + ":" + port);
        }
        DeviceMeterBean deviceMeterBean = JSONObject.parseObject(JSONObject.toJSONString(object), DeviceMeterBean.class);
        if (deviceMeterBean == null) {
           deviceMeterBean = new DeviceMeterBean();
           deviceMeterBean.setStatus("0");
        } else {
            if (deviceMeterBean.getTIME() == null) {
                logger.error("未获取到遥测数据 " + stakeNo + ":" + port);
                deviceMeterBean.setStatus("0");
            } else {
                deviceMeterBean.setStatus("1");
            }
        }
        return  deviceMeterBean;
    }

    @RequestMapping(path = "/getSignal", method = RequestMethod.POST)
    public DeviceSignalBean getSignal(String stakeNo, String port) {
        String keySignal = "AD:RTD:23:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keySignal);
        if (object != null)
        {
            logger.info(object.toString());
        } else {
            logger.error("未获取到遥信数据 " + stakeNo + ":" + port);
        }
        DeviceSignalBean deviceSignalBean = JSONObject.parseObject(JSONObject.toJSONString(object), DeviceSignalBean.class);
        if (deviceSignalBean == null) {
            deviceSignalBean = new DeviceSignalBean();
            deviceSignalBean.setStatus("0");
        } else {
            if (deviceSignalBean.getTIME() == null) {
                logger.error("未获取到遥信数据 " + stakeNo + ":" + port);
                deviceSignalBean.setStatus("0");
            } else {
                deviceSignalBean.setStatus("1");
            }
        }
        return deviceSignalBean;
    }

    @RequestMapping(path = "/getWorkState", method = RequestMethod.POST)
    public Map<String, String> getWorkState(String stakeNo, String port) {
        Map<String, String> mapRsp = new HashMap<>();
        String keySignal = "AD:RTD:23:" + stakeNo + ":" + port;
        DeviceSignalBean deviceSignalBean = JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keySignal)), DeviceSignalBean.class);
        if (deviceSignalBean != null)
        {
            mapRsp.put("workState", deviceSignalBean.getWORKSTATE());
            mapRsp.put("status", "1");
        } else {
            mapRsp.put("status", "0");
        }
        return mapRsp;
    }

    @RequestMapping(path = "/getElec", method = RequestMethod.POST)
    public DeviceElecBean getElec(String stakeNo, String port) {
        String keySignal = "AD:RTD:43:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keySignal);
        if (object != null)
        {
            logger.info(object.toString());
        } else {
            logger.error("未获取到设备计量数据 " + stakeNo + ":" + port);
        }
        DeviceElecBean deviceElecBean = JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keySignal)), DeviceElecBean.class);
        if (deviceElecBean == null) {
            deviceElecBean = new DeviceElecBean();
            deviceElecBean.setStatus("0");
        } else {
            if (deviceElecBean.getTIME() == null) {
                logger.error("未获取到设备计量数据 " + stakeNo + ":" + port);
                deviceElecBean.setStatus("0");
            } else {
                deviceElecBean.setStatus("1");
            }
        }
        return deviceElecBean;
    }

}
