package com.example.controlelec.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceMeterBean {
    /**
     * PP : 0
     * IO :
     * TIME : 20201221091035
     * UA : 231.2
     * UB : 231.2
     * CP :
     * UC : 231.2
     * BP :
     * AP : 0
     * IA : 0
     * IB : 0
     * IC : 0
     * QP :
     */

    @JSONField(name = "PP")
    private String PP;
    @JSONField(name = "IO")
    private String IO;
    @JSONField(name = "TIME")
    private String TIME;
    @JSONField(name = "UA")
    private String UA;
    @JSONField(name = "UB")
    private String UB;
    @JSONField(name = "CP")
    private String CP;
    @JSONField(name = "UC")
    private String UC;
    @JSONField(name = "BP")
    private String BP;
    @JSONField(name = "AP")
    private String AP;
    @JSONField(name = "IA")
    private String IA;
    @JSONField(name = "IB")
    private String IB;
    @JSONField(name = "IC")
    private String IC;
    @JSONField(name = "QP")
    private String QP;
    private String Status;

    public String getPP() {
        return PP;
    }

    public void setPP(String PP) {
        this.PP = PP;
    }

    public String getIO() {
        return IO;
    }

    public void setIO(String IO) {
        this.IO = IO;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getUA() {
        return UA;
    }

    public void setUA(String UA) {
        this.UA = UA;
    }

    public String getUB() {
        return UB;
    }

    public void setUB(String UB) {
        this.UB = UB;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getUC() {
        return UC;
    }

    public void setUC(String UC) {
        this.UC = UC;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getAP() {
        return AP;
    }

    public void setAP(String AP) {
        this.AP = AP;
    }

    public String getIA() {
        return IA;
    }

    public void setIA(String IA) {
        this.IA = IA;
    }

    public String getIB() {
        return IB;
    }

    public void setIB(String IB) {
        this.IB = IB;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getQP() {
        return QP;
    }

    public void setQP(String QP) {
        this.QP = QP;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}

