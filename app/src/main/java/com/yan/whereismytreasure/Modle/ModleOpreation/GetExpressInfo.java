package com.yan.whereismytreasure.Modle.ModleOpreation;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.ExpressInfoBean;
import com.yan.whereismytreasure.Modle.ModleInterface.ExpressInterface;

/**
 * 获取快递信息
 * Created by a7501 on 2016/5/24.
 */
public class GetExpressInfo implements ExpressInterface{

    private ExpressInfoBean expressInfoBean = null;

    @Override
    public ExpressInfoBean getExpInfo(String com,String no) {
        Parameters parameters = new Parameters();
        parameters.add("com","ems");
        parameters.add("no","9722704173801");
        JuheData.executeWithAPI(Global.mContext, 43, "http://v.juhe.cn/exp/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("ems","i:"+i+"\n"+"s:"+s);
                expressInfoBean = JSON.parseObject(s,ExpressInfoBean.class);
                Log.e("result", expressInfoBean.toString());

            }

            @Override
            public void onFinish() {
                Log.e("ems","Finish");
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.e("ems","i:"+i+"\n"+"s:"+s);
            }
        });

        return expressInfoBean;
    }
}
