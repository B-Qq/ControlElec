package com.example.controlelec.mapper;

import com.example.controlelec.bean.DeviceOnlineWarnBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceOnlineWarnMapper {
    List<DeviceOnlineWarnBean> getOnlineWarn(@Param("uuid") String uuid, @Param("date") String date, @Param("stakeNo") String stakeNo, @Param("warn") String warn);
}
