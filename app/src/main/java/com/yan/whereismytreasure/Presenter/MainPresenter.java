package com.yan.whereismytreasure.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Result;
import com.yan.whereismytreasure.Modle.Bean.TraceExpress;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.Modle.DB.DBManager;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.Servers.TraceServer;
import com.yan.whereismytreasure.UI.Adapter.ExpressAdapter;
import com.yan.whereismytreasure.UI.ViewInterface.MainInterface;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * main
 * Created by a7501 on 2016/5/28.
 */
public class MainPresenter extends MvpBasePresenter<MainInterface> {
    private ExpressInfoBean expInfo;
    private List<Lists> expressInfoList;

    private MyUser userInfo = BmobUser.getCurrentUser(Global.mContext, MyUser.class);

    /**
     * 查询快递
     *
     * @param com 公司代码
     * @param no  快递单号
     */
    public void getInfo(String com, final String no, final ListView listView) {

        getView().nowLoading();

        Parameters parameters = new Parameters();
        parameters.add("com", com);
        parameters.add("no", no);
        JuheData.executeWithAPI(Global.mContext, 43, "http://v.juhe.cn/exp/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("ems", "i:" + i + "\n" + "s:" + s);
                expInfo = JSON.parseObject(s, ExpressInfoBean.class);
                Log.e("ems", "exInfo:" + expInfo.toString());
                if (expInfo != null && expInfo.getReason().equals("成功的返回")) {

                    expressInfoList = expInfo.getResult().getList();
                    getView().showList();
                    listView.setAdapter(getAdapter());

                    checkUpdateAndSave(no);


                } else {
                    getView().noResult();
                }


            }

            @Override
            public void onFinish() {
                Log.e("ems", "Finish");
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                Log.e("ems", "i:" + i + "\n" + "s:" + s);
            }
        });
    }

    /**
     * 检查更新和保存
     * @param no
     */
    private void checkUpdateAndSave(String no) {
        DBManager.getInstance(Global.mContext)
                .doseHaveUpdate(no,expInfo.getResult().getList())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer > 0){
                            Log.e("update", "size "+integer);
                            List<Lists> mList = getUpdateLists(expInfo.getResult(),integer);
                            Log.e("update", "mList size "+mList.size());
                            saveExpResultListToDB(expInfo.getResult(),mList);
                            updateExpressInfo(expInfo.getResult(),mList);
                        }else {
                            Log.e("update", "all");
                            saveExpResultListToDB(expInfo.getResult(),expInfo.getResult().getList());
                            updateExpressInfo(expInfo.getResult(),expInfo.getResult().getList());
                        }

                    }


                });
    }

    private void saveExpResultListToDB(Result result,List<Lists> mList) {
        DBManager.getInstance(Global.mContext)
                .saveExpResultList(result,mList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    private List<Lists> getUpdateLists(Result result,Integer integer) {
        List<Lists> listses = result.getList();
        List<Lists> mList = new ArrayList<Lists>();
        for (int i = integer;i<listses.size();i++){
            mList.add(listses.get(i));
        }
        return mList;
    }


    public ExpressAdapter getAdapter() {
        if (expressInfoList != null) {
            ExpressAdapter expressAdapter = new ExpressAdapter(Global.mContext, expressInfoList, R.layout.item_express_info);
            return expressAdapter;
        }
        return null;

    }

    public void saveToSP(Context context, String number) {
        SharedPreferences.Editor saveNumber = context.getSharedPreferences("saveNumber", Context.MODE_PRIVATE).edit();
        saveNumber.putBoolean("isSave", true);
        saveNumber.putString("number", number);
        saveNumber.commit();
    }

    public String getNumberFromSP(Context context) {
        SharedPreferences saveNumber = context.getSharedPreferences("saveNumber", Context.MODE_PRIVATE);
        if (saveNumber.getBoolean("isSave", false)) {
            return saveNumber.getString("number", null);
        }
        return null;
    }

    public void updateExpressInfo(final Result result, final List<Lists> mlist) {


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                List<Lists> list = mlist;

                for (Lists mList :
                        list) {
                    mList.setExpressId(result.getNo());
                    mList.setCompany(result.getCompany());
                    mList.setUserId(userInfo.getObjectId());
                    mList.save(Global.mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("Main", "updateExpress Ok");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("Main", "updateExpressErr  " + s);
                        }
                    });

                    subscriber.onNext("updateExpress Ok");
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

    }

    private void updateLists(final List<Lists> list, final Result result) {
        final int[] count = {0};
        Lists mList = list.get(count[0]);
        mList.setResult(result);
        mList.setCompany(result.getCompany());
        mList.setUserId(userInfo.getObjectId());
        mList.save(Global.mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                count[0]++;
                if (count[0] == list.size() - 5)
                    return;
                updateLists(list, result);

                Log.e("Main", "updateExpress Ok");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("Main", "updateExpressErr  " + s);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Lists> list = result.getList();

                for (Lists mList :
                        list) {

                    Log.e("Main", list.toString());
                    //Lists mList = list.get(0);
                    mList.setCompany(result.getCompany());
                    mList.setUserId(userInfo.getObjectId());
                    mList.save(Global.mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Log.e("Main", "updateExpress Ok");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("Main", "updateExpressErr  " + s);
                        }
                    });

                    SystemClock.sleep(1);


                }

            }
        }).start();

    }


    /**
     * 保存快递追踪信息
     */
    public void traceExpress(){

            TraceExpress traceExpress = new TraceExpress();
            traceExpress.setUser(userInfo.getObjectId());
            traceExpress.setExpressId(expInfo.getResult().getNo());
            DBManager.getInstance(Global.mContext)
                    .traceExpress(traceExpress)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            getView().isTraceExpress();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean aBoolean) {

                        }
                    });

    }

    public void isTraceExpress(){

        if (expInfo != null)
        DBManager.getInstance(Global.mContext)
                .isTraceExpress(expInfo.getResult().getNo())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){
                            getView().isTraceExpress();
                        }else {
                            traceExpress();
                            getView().createDialog();
                        }
                    }
                });

    }

    /**
     * 更新追踪快递单号
     * @param title
     */
    public void updateTraceTitle(String title){
        if (expInfo != null){
            DBManager.getInstance(Global.mContext)
                    .updateTraceTitle(title,expInfo.getResult().getNo())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            Global.mContext.startService(new Intent(Global.mContext,TraceServer.class));

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Boolean aBoolean) {

                        }
                    });
        }

    }
}
