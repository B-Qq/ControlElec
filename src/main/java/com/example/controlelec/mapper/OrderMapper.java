package com.example.controlelec.mapper;

import com.example.controlelec.bean.OrderBean;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    OrderBean getOrderId(@Param("stakeNo") String stakeNo, @Param("port") String port);
}
