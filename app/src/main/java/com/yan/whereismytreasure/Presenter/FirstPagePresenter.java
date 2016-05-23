package com.yan.whereismytreasure.Presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.ExpressInfo.ExpressInfoBean;
import com.yan.whereismytreasure.UI.ViewInterface.FirstPageInterface;

/**
 * FirstPagePresenter
 * Created by a7501 on 2016/5/22.
 */
public class FirstPagePresenter extends MvpBasePresenter<FirstPageInterface> {


    /**
     * 查询快递
     * @param com 公司代码
     * @param no  快递单号
     */
    public void getInfo(String com,String no){

        Parameters parameters = new Parameters();
        parameters.add("com","ems");
        parameters.add("no","9722704173801");
        JuheData.executeWithAPI(Global.mContext, 43, "http://v.juhe.cn/exp/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("ems","i:"+i+"\n"+"s:"+s);
                ExpressInfoBean expressInfoBean = JSON.parseObject(s,ExpressInfoBean.class);
                Log.e("result",expressInfoBean.toString());

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

    }

}
