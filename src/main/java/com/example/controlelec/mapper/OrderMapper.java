package com.example.controlelec.mapper;

import com.example.controlelec.bean.OrderBean;
import com.example.controlelec.bean.OrderRecordBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    OrderBean getOrderId(@Param("stakeNo") String stakeNo, @Param("port") String port);
    List<OrderRecordBean> getOrders(@Param("uuid") String uuid, @Param("date") String date, @Param("stakeNo") String stakeNo, @Param("orderId") String orderId);
}
