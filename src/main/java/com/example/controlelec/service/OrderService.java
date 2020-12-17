package com.example.controlelec.service;

import com.example.controlelec.bean.OrderBean;

public interface OrderService {
    OrderBean getOrderId(String stakeNo, String port);
}
