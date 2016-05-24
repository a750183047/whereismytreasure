package com.yan.whereismytreasure.Modle.Bean.ExpressInfo;

/**
 * list
 * Created by a7501 on 2016/5/23.
 */
public class Lists {
    private String datetime;   //时间
    private String remark;    //描述
    private String zone;     //区域

    @Override
    public String toString() {
        return "Lists{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
