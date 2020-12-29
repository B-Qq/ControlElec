package com.example.controlelec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.controlelec.service.OrderService;
import com.example.controlelec.bean.OrderBean;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @RequestMapping(path = "/getOrderId", method = RequestMethod.POST)
    public Map<String, String> getOrderId(String stakeNo, String port) {
        Map<String, String> mapRsp = new HashMap<>(1);
        OrderBean orderBean = orderService.getOrderId(stakeNo, port);
        if (orderBean != null) {
            logger.info("获取订单号为" + orderBean.getOrderId());
            mapRsp.put("orderId", orderBean.getOrderId());
        } else {
            logger.info("未获取到订单号");
            mapRsp.put("orderId", "");
        }
        return mapRsp;
    }
}
