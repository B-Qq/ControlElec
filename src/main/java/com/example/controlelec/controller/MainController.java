package com.example.controlelec.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.controlelec.bean.DeviceBean;
import com.example.controlelec.bean.OrderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.controlelec.service.DeviceService;
import org.springframework.web.client.RestTemplate;
import com.example.controlelec.comm.ControlElecReason;

import java.util.*;

@RestController
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DeviceService deviceService;

    @RequestMapping(path = "/getDeviceList", method = RequestMethod.POST)
    public Map<String, List<DeviceBean>> getDeviceList(String uuid) {
        List<DeviceBean> deviceBeanList;
        if (uuid.equals("bcb73132-3b71-11eb-ab4e-000c29a9186e")) {
            deviceBeanList = deviceService.getAllDeviceList();
        } else {
            deviceBeanList = deviceService.getDeviceList(uuid);
        }
        Map<String, List<DeviceBean>> mapRsp = new HashMap<String, List<DeviceBean>>(1);
        try {
            //未获取到数据
            if (deviceBeanList.isEmpty()) {
                logger.warn("未获取到设备信息");
                mapRsp.put("stakes", deviceBeanList);
            } else {
                logger.info("获取到设备信息");
                mapRsp.put("stakes", deviceBeanList);
            }
            return mapRsp;
        } catch (Exception e) {
            logger.error("获取设备信息异常" + e.getMessage());
            mapRsp.put("stakes", deviceBeanList);
            return mapRsp;
        }
    }

    @RequestMapping(path = "/startElec", method = RequestMethod.POST)
    public Map<String, String> startElec(String stakeNo, String port) {
        logger.info("桩:" + stakeNo + " 端口:" + port + " 下供电指令");
        Map<String, String> mapRsp = new HashMap<>(1);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("balance", "0");
        mapForm.add("stakeNo", stakeNo);
        mapForm.add("chargePort", port);
        mapForm.add("provinceId", "2001");
        mapForm.add("cityId", "1001");
        mapForm.add("stationId", "33050000001");
        mapForm.add("ct", "100/5");
        mapForm.add("pt", "1");
        mapForm.add("ratedPower", "500");
        mapForm.add("boatName", "阿萨大1231231234");
        mapForm.add("token", "c:app:5C0D464F470E41D39A5A710D78761AEF1");
        mapForm.add("userId", "139010");
        mapForm.add("dateNode", "2018-12-25");

        JSONObject result = restTemplate.postForObject("http://192.168.102.118:8080/hsf/adcloud/interface/notStationBeginsToSmallStake", mapForm, JSONObject.class);
        if (result != null) {
            logger.info("供电返回结果:" + result.toJSONString());
            JSONObject dataObj = result.getJSONObject("data");
            String type = dataObj.getString("type");
            String msgId = dataObj.getString("msg");
            if (type.equals("1")) {
                String orderId = dataObj.getString("orderId");
                mapRsp.put("status", "1");
                mapRsp.put("orderId", orderId);
                mapRsp.put("msg", "申请用电成功");
            } else if (type.equals("0")) {
                mapRsp.put("status", "0");
                mapRsp.put("msg", ControlElecReason.getStartElecReason(msgId));
            } else {
                mapRsp.put("status", "0");
                mapRsp.put("msg", "下订单成功下供电指令失败");
            }
        } else {
            mapRsp.put("status", "0");
            mapRsp.put("msg", "返回结果为空");
        }
        return mapRsp;
    }

    @RequestMapping(path = "/stopElec", method = RequestMethod.POST)
    public Map<String, String> stopElec(String orderId) {
        Map<String, String> mapRsp = new HashMap<>(1);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("userId", "1390109");
        mapForm.add("dateNode", "20180812");
        mapForm.add("orderId", orderId);
        logger.info("订单:" + orderId + " 下停电指令");
        JSONObject result = restTemplate.postForObject("http://192.168.102.118:8080/hsf/adcloud/interface/notStationEndToSmallStake", mapForm, JSONObject.class);
        if (result != null) {
            logger.info("断电返回结果:" + result.toJSONString());
            JSONObject dataObj = result.getJSONObject("data");
            String type = dataObj.getString("type");
            String msgId = dataObj.getString("msg");
            if (type.equals("1")) {
                mapRsp.put("status", "1");
                mapRsp.put("msg", "停止用电成功");
            } else if (type.equals("0")) {
                mapRsp.put("status", "0");
                mapRsp.put("msg", ControlElecReason.getStopElecReason(msgId));
            } else {
                mapRsp.put("status", "0");
                mapRsp.put("msg", "输入参数为空");
            }
        } else {
            mapRsp.put("status", "0");
            mapRsp.put("msg", "返回结果为空");
        }
        return mapRsp;
    }
}
