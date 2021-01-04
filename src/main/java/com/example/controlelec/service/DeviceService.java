package com.example.controlelec.service;

import com.example.controlelec.bean.DeviceBean;

import java.util.List;

public interface DeviceService {
    /**
     * 获取指定用户对应的设备信息
     *
     * @param uuid 用户uuid
     * @return 设备信息
     */
    List<DeviceBean> getDeviceList(String uuid);

    /**
     * 获取管理员用户对用的设备信息
     *
     * @return 设备信息
     */
    List<DeviceBean> getAllDeviceList();

    /**
     * 获取设备信息
     */
    DeviceBean getDeviceInfo(String stakeNo);

}
