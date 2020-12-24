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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(path = "/getMeter", method = RequestMethod.POST)
    public DeviceMeterBean getMeter(String stakeNo, String port) {
        String keyMeter = "AD:RTD:33:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keyMeter);
        logger.info(object.toString());
        return JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keyMeter)), DeviceMeterBean.class);
    }

    @RequestMapping(path = "/getSignal", method = RequestMethod.POST)
    public DeviceSignalBean getSignal(String stakeNo, String port) {
        String keySignal = "AD:RTD:23:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keySignal);
        logger.info(object.toString());
        return JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keySignal)), DeviceSignalBean.class);
    }

    @RequestMapping(path = "/getWorkState", method = RequestMethod.POST)
    public Map<String, String> getWorkState(String stakeNo, String port) {
        Map<String, String> mapRsp = new HashMap<>();
        String keySignal = "AD:RTD:23:" + stakeNo + ":" + port;
        DeviceSignalBean deviceSignalBean = JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keySignal)), DeviceSignalBean.class);
        mapRsp.put("workState", deviceSignalBean.getWORKSTATE());
        return mapRsp;
    }

    @RequestMapping(path = "/getElec", method = RequestMethod.POST)
    public DeviceElecBean getElec(String stakeNo, String port) {
        String keySignal = "AD:RTD:43:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keySignal);
        logger.info(object.toString());
        return JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keySignal)), DeviceElecBean.class);
    }

}
