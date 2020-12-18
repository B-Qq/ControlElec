package com.example.controlelec.service;

import com.example.controlelec.bean.OrderBean;

public interface OrderService {
    /**
     * 获取订单号
     * @param stakeNo 桩号
     * @param port 端口号
     * @return 订单号
     */
    OrderBean getOrderId(String stakeNo, String port);
}
