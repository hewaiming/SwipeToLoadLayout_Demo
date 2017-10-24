package com.hewaiming.administrator.swipetoloadlayout_demo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class AlarmRecord implements Serializable {
    private String PotNo;
    private String RecNo;
    private String DDate;

    public AlarmRecord() {
    }

    public AlarmRecord(String potNo, String recNo, String DDate) {
        PotNo = potNo;
        RecNo = recNo;
        this.DDate = DDate;
    }

    public AlarmRecord(String potNo, String DDate) {
        PotNo = potNo;
        this.DDate = DDate;
    }

    public String getPotNo() {
        return PotNo;
    }

    public String getRecNo() {
        return RecNo;
    }

    public String getDDate() {
        return DDate;
    }

    public void setPotNo(String potNo) {
        PotNo = potNo;
    }

    public void setRecNo(String recNo) {
        RecNo = recNo;
    }

    public void setDDate(String DDate) {
        this.DDate = DDate;
    }

    @Override
    public String toString() {
        return "AlarmRecord{" +
                "PotNo='" + PotNo + '\'' +
                ", RecNo='" + RecNo + '\'' +
                ", DDate='" + DDate + '\'' +
                '}';
    }
}
