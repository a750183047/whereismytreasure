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
import android.provider.Settings;
import android.util.Log;

import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewActivity.FirstPageActivity;
import com.yan.whereismytreasure.UI.ViewActivity.SplashActivity;

public class TraceServer extends Service {
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
        Log.e("server","开启服务");


        //  notificationNotify();

    }

    /**
     * 弹出通知栏通知
     * @param title 快递名称
     * @param no    快递单号
     */
    private void notificationNotify(String title,String no) {
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Notification.Builder builder = new Notification.Builder(Global.mContext);
        builder.setContentTitle("物流有更新啦")
                .setTicker("快递物流更新啦")
                .setContentText("物流 "+title+" 有更新啦，快来看看吧")
                .setSmallIcon(R.mipmap.icon_new_big)
                .setContentIntent(pendingIntent)       //设置点击后进入的活动
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.icon_new_big));
        notification = builder.build();
        manager.notify(Integer.valueOf(no),notification);
    }
}
