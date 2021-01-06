package com.example.controlelec.controller;

import com.example.controlelec.bean.DeviceOnlineWarnBean;
import com.example.controlelec.service.DeviceOnlineWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceOnlineWarnController {
    @Autowired
    private DeviceOnlineWarnService deviceOnlineWarnService;

    @RequestMapping(path = "/getOnlineWarn", method = RequestMethod.POST)
    public List<DeviceOnlineWarnBean> getOnlineWarn(String uuid, String date, String stakeNo, String warn){
       List<DeviceOnlineWarnBean> deviceOnlineWarnBeanList = deviceOnlineWarnService.getOnlineWarn(uuid, date, stakeNo, warn);
       return deviceOnlineWarnBeanList;
    }
}
