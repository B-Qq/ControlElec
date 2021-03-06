package com.example.controlelec.service;

import java.util.List;
import com.example.controlelec.bean.DeviceOnlineWarnBean;

public interface DeviceOnlineWarnService {
    /**
     * 获取设备在线告警
     * @param uuid 用户uuid
     * @param date 日期
     * @param stakeNo 桩号
     * @param warn 告警状态
     * @return
     */
    List<DeviceOnlineWarnBean> getOnlineWarn(String uuid, String date, String stakeNo, String warn);
}
