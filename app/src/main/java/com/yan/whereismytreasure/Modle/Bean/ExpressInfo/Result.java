package com.yan.whereismytreasure.Modle.Bean.ExpressInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * result
 * Created by a7501 on 2016/5/23.
 */
public class Result extends BmobObject{

    private String userId; //用户ID
    private String company;  //公司
    private String com;
    private String no;
    private List<Lists> list = new ArrayList<Lists>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "company='" + company + '\'' +
                ", com='" + com + '\'' +
                ", no='" + no + '\'' +
                ", list=" + list +
                '}';
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<Lists> getList() {
        return list;
    }

    public void setList(List<Lists> list) {
        this.list = list;
    }
}
