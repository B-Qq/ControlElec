package com.example.controlelec.serviceimpl;

import com.example.controlelec.bean.DeviceBean;
import com.example.controlelec.mapper.DeviceMapper;
import com.example.controlelec.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<DeviceBean> getAllDeviceList() {
        return deviceMapper.getAllDeviceList();
    }

    @Override
    public List<DeviceBean> getDeviceList(String uuid) {
        return deviceMapper.getDeviceList(uuid);
    }

    @Override
    public DeviceBean getDeviceInfo(String stakeNo) {
        return deviceMapper.getDeviceInfo(stakeNo);
    }
}
