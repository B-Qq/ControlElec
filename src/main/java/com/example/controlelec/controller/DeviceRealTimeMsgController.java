package com.example.controlelec.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.controlelec.util.RedisUtil;
import com.example.controlelec.bean.DeviceMeterBean;

@RestController
public class DeviceRealTimeMsgController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(path = "/getMeter", method = RequestMethod.POST)
    public DeviceMeterBean getMeter(String stakeNo, String port) {
        DeviceMeterBean deviceMeterBean = null;
        String keyMeter = "AD:RTD:33:" + stakeNo + ":" + port;
        Object object = redisUtil.get(keyMeter);
        logger.info(object.toString());
        deviceMeterBean = JSONObject.parseObject(JSONObject.toJSONString(redisUtil.get(keyMeter)), DeviceMeterBean.class);
        return deviceMeterBean;
    }

}
