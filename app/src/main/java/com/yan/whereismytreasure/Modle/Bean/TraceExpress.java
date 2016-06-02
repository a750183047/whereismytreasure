package com.yan.whereismytreasure.Modle.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 快递追踪
 * Created by a7501 on 2016/6/2.
 */
public class TraceExpress extends BmobObject{
    private String user;
    private String expressId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }
}
