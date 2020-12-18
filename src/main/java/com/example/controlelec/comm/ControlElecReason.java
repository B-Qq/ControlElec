package com.example.controlelec.comm;

import java.util.HashMap;
import java.util.Map;


public class ControlElecReason {
    private final static Map<String, String> MAP_START_REASON;
    private final static Map<String, String> MAP_STOP_REASON;
    static
    {
        MAP_START_REASON = new HashMap<>();
        MAP_START_REASON.put("1","电缆未连接");
        MAP_START_REASON.put("2","冻结金未成功");
        MAP_START_REASON.put("3","输入参数为空");
        MAP_START_REASON.put("5","设备端口已占用");
        MAP_START_REASON.put("8","订单信息存储异常");
        MAP_START_REASON.put("31","桩离线");
        MAP_START_REASON.put("32","站离线");
        MAP_START_REASON.put("33","站内控离线");
        MAP_START_REASON.put("34","未获取到桩/站/站内控/端口/线缆状态");
        MAP_START_REASON.put("35","端口故障【端口工作状态为告警】");
        MAP_START_REASON.put("36","桩认证未通过");
        MAP_START_REASON.put("61","计费模型不完整");

        MAP_STOP_REASON = new HashMap<>();
        MAP_STOP_REASON.put("2","输入参数为空");
        MAP_STOP_REASON.put("12","系统异常");
        MAP_STOP_REASON.put("13","停电指令异常");
        MAP_STOP_REASON.put("14","返回停电失败标识");
        MAP_STOP_REASON.put("22","不是供电中的订单");
        MAP_STOP_REASON.put("23","数据信息不全");
        MAP_STOP_REASON.put("31","桩离线");
        MAP_STOP_REASON.put("32","站离线");
        MAP_STOP_REASON.put("33","站内控离线");
        MAP_STOP_REASON.put("34","未获取到桩/站/站内控/端口/线缆状态");
    };

    public static String getStartElecReason(String msgId) {
        return MAP_START_REASON.getOrDefault(msgId, "未知原因");
    }

    public static String getStopElecReason(String msgId) {
        return MAP_STOP_REASON.getOrDefault(msgId, "未知原因");
    }
}
