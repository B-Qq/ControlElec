package com.example.controlelec.comm;

import java.util.HashMap;
import java.util.Map;


public class ControlElecReason {
    private final static Map<String, String> mapStartReason;
    private final static Map<String, String> mapStopReason;
    static
    {
        mapStartReason = new HashMap<>();
        mapStartReason.put("1","电缆未连接");
        mapStartReason.put("2","冻结金未成功");
        mapStartReason.put("3","输入参数为空");
        mapStartReason.put("5","设备端口已占用");
        mapStartReason.put("8","订单信息存储异常");
        mapStartReason.put("31","桩离线");
        mapStartReason.put("32","站离线");
        mapStartReason.put("33","站内控离线");
        mapStartReason.put("34","未获取到桩/站/站内控/端口/线缆状态");
        mapStartReason.put("35","端口故障【端口工作状态为告警】");
        mapStartReason.put("36","桩认证未通过");
        mapStartReason.put("61","计费模型不完整");

        mapStopReason = new HashMap<>();
        mapStopReason.put("2","输入参数为空");
        mapStopReason.put("12","系统异常");
        mapStopReason.put("13","停电指令异常");
        mapStopReason.put("14","返回停电失败标识");
        mapStopReason.put("22","不是供电中的订单");
        mapStopReason.put("23","数据信息不全");
        mapStopReason.put("31","桩离线");
        mapStopReason.put("32","站离线");
        mapStopReason.put("33","站内控离线");
        mapStopReason.put("34","未获取到桩/站/站内控/端口/线缆状态");
    };

    public static String getStartElecReason(String msgId) {
        return mapStartReason.getOrDefault(msgId, "未知原因");
    }

    public static String getStopElecReason(String msgId) {
        return mapStopReason.getOrDefault(msgId, "未知原因");
    }
}
