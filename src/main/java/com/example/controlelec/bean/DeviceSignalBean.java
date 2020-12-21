package com.example.controlelec.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceSignalBean {
    /**
     * LP_WARN : 0
     * SHORT_CIRCUIT : 0
     * STATIONID : 33050000005
     * STAKENO : 3305060002000006
     * CHARGEPORT : 1
     * TIME : 20201218164706
     * DUMP_ERROR : 0
     * RELAY_STATE : 0
     * OV_WARN : 0
     * OC_ERROR : 0
     * UV_WARN : 0
     * WATER_ERROR : 0
     * SCRAM_ERROR : 0
     * SPD_ERROR : 0
     * METER_ERROR : 0
     * ACCESS_ERROR : 0
     * LEAKAGE_ELEC : 0
     * SWITCH_STATE : 1
     * WORK_STATE : 2
     */

    @JSONField(name = "LP_WARN")
    private String LPWARN;
    @JSONField(name = "SHORT_CIRCUIT")
    private String SHORTCIRCUIT;
    @JSONField(name = "STATIONID")
    private String STATIONID;
    @JSONField(name = "STAKENO")
    private String STAKENO;
    @JSONField(name = "CHARGEPORT")
    private String CHARGEPORT;
    @JSONField(name = "TIME")
    private String TIME;
    @JSONField(name = "DUMP_ERROR")
    private String DUMPERROR;
    @JSONField(name = "RELAY_STATE")
    private String RELAYSTATE;
    @JSONField(name = "OV_WARN")
    private String OVWARN;
    @JSONField(name = "OC_ERROR")
    private String OCERROR;
    @JSONField(name = "UV_WARN")
    private String UVWARN;
    @JSONField(name = "WATER_ERROR")
    private String WATERERROR;
    @JSONField(name = "SCRAM_ERROR")
    private String SCRAMERROR;
    @JSONField(name = "SPD_ERROR")
    private String SPDERROR;
    @JSONField(name = "METER_ERROR")
    private String METERERROR;
    @JSONField(name = "ACCESS_ERROR")
    private String ACCESSERROR;
    @JSONField(name = "LEAKAGE_ELEC")
    private String LEAKAGEELEC;
    @JSONField(name = "SWITCH_STATE")
    private String SWITCHSTATE;
    @JSONField(name = "WORK_STATE")
    private String WORKSTATE;

    public String getLPWARN() {
        return LPWARN;
    }

    public void setLPWARN(String LPWARN) {
        this.LPWARN = LPWARN;
    }

    public String getSHORTCIRCUIT() {
        return SHORTCIRCUIT;
    }

    public void setSHORTCIRCUIT(String SHORTCIRCUIT) {
        this.SHORTCIRCUIT = SHORTCIRCUIT;
    }

    public String getSTATIONID() {
        return STATIONID;
    }

    public void setSTATIONID(String STATIONID) {
        this.STATIONID = STATIONID;
    }

    public String getSTAKENO() {
        return STAKENO;
    }

    public void setSTAKENO(String STAKENO) {
        this.STAKENO = STAKENO;
    }

    public String getCHARGEPORT() {
        return CHARGEPORT;
    }

    public void setCHARGEPORT(String CHARGEPORT) {
        this.CHARGEPORT = CHARGEPORT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDUMPERROR() {
        return DUMPERROR;
    }

    public void setDUMPERROR(String DUMPERROR) {
        this.DUMPERROR = DUMPERROR;
    }

    public String getRELAYSTATE() {
        return RELAYSTATE;
    }

    public void setRELAYSTATE(String RELAYSTATE) {
        this.RELAYSTATE = RELAYSTATE;
    }

    public String getOVWARN() {
        return OVWARN;
    }

    public void setOVWARN(String OVWARN) {
        this.OVWARN = OVWARN;
    }

    public String getOCERROR() {
        return OCERROR;
    }

    public void setOCERROR(String OCERROR) {
        this.OCERROR = OCERROR;
    }

    public String getUVWARN() {
        return UVWARN;
    }

    public void setUVWARN(String UVWARN) {
        this.UVWARN = UVWARN;
    }

    public String getWATERERROR() {
        return WATERERROR;
    }

    public void setWATERERROR(String WATERERROR) {
        this.WATERERROR = WATERERROR;
    }

    public String getSCRAMERROR() {
        return SCRAMERROR;
    }

    public void setSCRAMERROR(String SCRAMERROR) {
        this.SCRAMERROR = SCRAMERROR;
    }

    public String getSPDERROR() {
        return SPDERROR;
    }

    public void setSPDERROR(String SPDERROR) {
        this.SPDERROR = SPDERROR;
    }

    public String getMETERERROR() {
        return METERERROR;
    }

    public void setMETERERROR(String METERERROR) {
        this.METERERROR = METERERROR;
    }

    public String getACCESSERROR() {
        return ACCESSERROR;
    }

    public void setACCESSERROR(String ACCESSERROR) {
        this.ACCESSERROR = ACCESSERROR;
    }

    public String getLEAKAGEELEC() {
        return LEAKAGEELEC;
    }

    public void setLEAKAGEELEC(String LEAKAGEELEC) {
        this.LEAKAGEELEC = LEAKAGEELEC;
    }

    public String getSWITCHSTATE() {
        return SWITCHSTATE;
    }

    public void setSWITCHSTATE(String SWITCHSTATE) {
        this.SWITCHSTATE = SWITCHSTATE;
    }

    public String getWORKSTATE() {
        return WORKSTATE;
    }

    public void setWORKSTATE(String WORKSTATE) {
        this.WORKSTATE = WORKSTATE;
    }
}
