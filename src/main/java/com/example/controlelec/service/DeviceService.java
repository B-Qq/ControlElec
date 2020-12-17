package com.example.controlelec.service;

import com.example.controlelec.bean.DeviceBean;

import java.util.List;

public interface DeviceService {
    List<DeviceBean> getDeviceList(String uuid);
    List<DeviceBean> getAllDeviceList();
}
