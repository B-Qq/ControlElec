package com.example.controlelec.service;

import com.example.controlelec.bean.OrderBean;
import com.example.controlelec.bean.OrderRecordBean;

import java.util.List;

public interface OrderService {
    /**
     * 获取订单号
     * @param stakeNo 桩号
     * @param port 端口号
     * @return 订单号
     */
    OrderBean getOrderId(String stakeNo, String port);

    /**
     * 获取订单记录
     * @param uuid 用户uuid
     * @param date 订单创建时间
     * @param stakeNo  桩号
     * @param orderId 订单号
     * @return
     */
    List<OrderRecordBean> getOrders(String uuid, String date, String stakeNo, String orderId);
}
