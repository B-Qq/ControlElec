package com.example.controlelec.serviceimpl;

import com.example.controlelec.bean.OrderBean;
import com.example.controlelec.bean.OrderRecordBean;
import com.example.controlelec.mapper.OrderMapper;
import com.example.controlelec.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<OrderRecordBean> getOrders(String uuid, String date, String stakeNo, String orderId) {
        return orderMapper.getOrders(uuid, date, stakeNo, orderId);
    }

    @Override
    public OrderBean getOrderId(String stakeNo, String port) {
        return orderMapper.getOrderId(stakeNo, port);
    }
}
