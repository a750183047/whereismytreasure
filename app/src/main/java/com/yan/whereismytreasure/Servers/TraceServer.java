package com.yan.whereismytreasure.Servers;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.ExpressInfoBean;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Lists;
import com.yan.whereismytreasure.Modle.Bean.TraceExpress;
import com.yan.whereismytreasure.Modle.DB.DBManager;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewActivity.FirstPageActivity;
import com.yan.whereismytreasure.UI.ViewActivity.SplashActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class TraceServer extends Service {

    private List<TraceExpress> list = new ArrayList<TraceExpress>();
    private ExpressInfoBean expInfo;
    private List<Lists> expressInfoList;

    public TraceServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("server", "开启服务");

        // Timer timer = new Timer(true);

        // TimerTask timerTask = new TimerTask() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                if (Looper.myLooper() == null)
                    Looper.prepare();

                while (true) {
                    DBManager.getInstance(Global.mContext)
                            .getAllTraceExpress()
                            .subscribe(new Subscriber<List<TraceExpress>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(List<TraceExpress> traceExpresses) {
                                    list = traceExpresses;
                                }
                            });

                    SystemClock.sleep(500);  //等待列表加载完
                    if (list != null) {
                        Log.e("Server", "000" + list.size());
                        for (TraceExpress tace :
                                list) {
                            Log.e("Server", "000" + tace.getExpressId());
                            searchExpress(tace.getCompany(), tace.getExpressId(), tace.getTitle());
                            SystemClock.sleep( 1000);
                        }
                    }

                    SystemClock.sleep(60 * 1000);
                }


            }
        }).start();

        // timer.schedule(timerTask,0, 60 * 1000);


        //  notificationNotify();

    }

    private void searchExpress(String com, final String no, final String title) {
        notificationNotify(title, no);
        Parameters parameters = new Parameters();
        parameters.add("com", com);
        parameters.add("no", no);
        JuheData.executeWithAPI(Global.mContext, 43, "http://v.juhe.cn/exp/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                Log.e("ems", "onSuccess");
                expInfo = JSON.parseObject(s, ExpressInfoBean.class);
                if (expInfo != null && expInfo.getReason().equals("成功的返回")) {

                    expressInfoList = expInfo.getResult().getList();

                    //和数据库保存的列表作比对，如果条数大于数据库的，则弹出通知
                    if (true) {
                        notificationNotify(title, no);
                    }

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
     * 弹出通知栏通知
     *
     * @param title 快递名称
     * @param no    快递单号
     */
    private void notificationNotify(String title, String no) {
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Notification.Builder builder = new Notification.Builder(Global.mContext);
        builder.setContentTitle("物流有更新啦")
                .setTicker("快递物流更新啦")
                .setContentText("物流 " + title + " 有更新啦，快来看看吧")
                .setSmallIcon(R.mipmap.icon_new_big)
                .setContentIntent(pendingIntent)       //设置点击后进入的活动
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_new_big));
        notification = builder.build();
        manager.notify(1, notification);
    }
}
