package com.example.controlelec.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.controlelec.bean.DeviceOnlineWarnBean;
import com.example.controlelec.mapper.DeviceOnlineWarnMapper;
import com.example.controlelec.service.DeviceOnlineWarnService;

import java.util.List;

@Service
public class DeviceOnlineWarnServiceImpl implements DeviceOnlineWarnService {

    @Autowired
    private DeviceOnlineWarnMapper deviceOnlineWarnMapper;

    @Override
    public List<DeviceOnlineWarnBean> getOnlineWarn(String uuid, String date, String stakeNo, String warn) {
        List<DeviceOnlineWarnBean> deviceOnlineWarnBeanList = deviceOnlineWarnMapper.getOnlineWarn(uuid, date, stakeNo, warn);
        return deviceOnlineWarnBeanList;
    }
}
