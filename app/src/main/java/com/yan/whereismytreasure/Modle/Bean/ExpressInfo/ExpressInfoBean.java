package com.yan.whereismytreasure.Modle.Bean.ExpressInfo;

/**
 * 快递信息
 * Created by a7501 on 2016/5/23.
 */
public class ExpressInfoBean {
    private String resultcode;  //标识码
    private String reason;
    private Result result;

    @Override
    public String toString() {
        return "ExpressInfoBean{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
