package com.example.controlelec.serviceimpl;

import com.example.controlelec.bean.OrderBean;
import com.example.controlelec.mapper.OrderMapper;
import com.example.controlelec.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public OrderBean getOrderId(String stakeNo, String port) {
        return orderMapper.getOrderId(stakeNo, port);
    }
}
