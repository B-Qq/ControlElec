package com.example.controlelec.bean;

public class DeviceOnlineWarnBean {
    /**
     * 桩名称
     */
    public String stakeName;
    /**
     * 端口号
     */
    public String port;
    /**
     * 告警开始时间
     */
    public String warnStartTime;
    /**
     * 告警结束时间
     */
    public String warnEndTime;
    /**
     * 告警状态
     */
    public String status;

    public String getStakeName() {
        return stakeName;
    }

    public void setStakeName(String stakeName) {
        this.stakeName = stakeName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getWarnStartTime() {
        return warnStartTime;
    }

    public void setWarnStartTime(String warnStartTime) {
        this.warnStartTime = warnStartTime;
    }

    public String getWarnEndTime() {
        return warnEndTime;
    }

    public void setWarnEndTime(String warnEndTime) {
        this.warnEndTime = warnEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
