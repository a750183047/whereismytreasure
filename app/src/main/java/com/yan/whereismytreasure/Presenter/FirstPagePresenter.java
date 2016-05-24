package com.yan.whereismytreasure.Presenter;

import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.ExpressInfoBean;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Lists;
import com.yan.whereismytreasure.Modle.ModleOpreation.GetExpressInfo;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.Adapter.ExpressAdapter;
import com.yan.whereismytreasure.UI.ViewInterface.FirstPageInterface;

import java.util.List;

/**
 * FirstPagePresenter
 * Created by a7501 on 2016/5/22.
 */
public class FirstPagePresenter extends MvpBasePresenter<FirstPageInterface> {


    private ExpressInfoBean expInfo;
    private List<Lists> expressInfoList;

    /**
     * 查询快递
     * @param com 公司代码
     * @param no  快递单号
     */
    public void getInfo(String com, String no, final ListView listView){

        getView().nowLoading();

        Parameters parameters = new Parameters();
        parameters.add("com",com);
        parameters.add("no",no);
        JuheData.executeWithAPI(Global.mContext, 43, "http://v.juhe.cn/exp/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("ems","i:"+i+"\n"+"s:"+s);
                expInfo = JSON.parseObject(s,ExpressInfoBean.class);
                Log.e("ems","exInfo:"+expInfo.toString());
                if (expInfo != null&&expInfo.getReason().equals("成功的返回")){

                    expressInfoList = expInfo.getResult().getList();
                    getView().showList();
                    listView.setAdapter(getAdapter());
                }else {
                    getView().noResult();
                }



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


    public ExpressAdapter getAdapter(){
        if (expressInfoList != null){
            ExpressAdapter expressAdapter = new ExpressAdapter(Global.mContext,expressInfoList, R.layout.item_express_info);
            return expressAdapter;
        }
        return null;

    }

}
