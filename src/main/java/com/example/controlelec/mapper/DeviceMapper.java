package com.example.controlelec.mapper;

import com.example.controlelec.bean.DeviceBean;

import java.util.List;

public interface DeviceMapper {
    List<DeviceBean> getDeviceList(String uuid);
    List<DeviceBean> getAllDeviceList();
}
