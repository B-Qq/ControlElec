package com.example.controlelec.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceElecBean {
    /**
     * SPORT_ELEC : 527.18
     * VALLEY_ELEC : 526.19
     * TIME : 20201224153625
     * PEAK_ELEC : 530
     * TOTAL_ELEC : 2108.2
     * FLAT_ELEC : 524.82
     */

    @JSONField(name = "SPORT_ELEC")
    private String SPORTELEC;
    @JSONField(name = "VALLEY_ELEC")
    private String VALLEYELEC;
    @JSONField(name = "TIME")
    private String TIME;
    @JSONField(name = "PEAK_ELEC")
    private String PEAKELEC;
    @JSONField(name = "TOTAL_ELEC")
    private String TOTALELEC;
    @JSONField(name = "FLAT_ELEC")
    private String FLATELEC;
    private String Status;

    public String getSPORTELEC() {
        return SPORTELEC;
    }

    public void setSPORTELEC(String SPORTELEC) {
        this.SPORTELEC = SPORTELEC;
    }

    public String getVALLEYELEC() {
        return VALLEYELEC;
    }

    public void setVALLEYELEC(String VALLEYELEC) {
        this.VALLEYELEC = VALLEYELEC;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getPEAKELEC() {
        return PEAKELEC;
    }

    public void setPEAKELEC(String PEAKELEC) {
        this.PEAKELEC = PEAKELEC;
    }

    public String getTOTALELEC() {
        return TOTALELEC;
    }

    public void setTOTALELEC(String TOTALELEC) {
        this.TOTALELEC = TOTALELEC;
    }

    public String getFLATELEC() {
        return FLATELEC;
    }

    public void setFLATELEC(String FLATELEC) {
        this.FLATELEC = FLATELEC;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
