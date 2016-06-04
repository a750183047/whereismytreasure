package com.yan.whereismytreasure.Modle.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Lists;
import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Result;
import com.yan.whereismytreasure.Modle.Bean.TraceExpress;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;

import java.util.List;

import cn.bmob.v3.BmobUser;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * db manager
 * Created by a7501 on 2016/6/1.
 */
public class DBManager implements IDBManager {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "express_info.db";

    /**
     * 版本信息
     */
    public static final int VERSION = 2;

    /**
     * 实例化
     */
    private static DBManager dbManager;
    private SQLiteDatabase sqLiteDatabase;
    /**
     * 用户信息
     */
    private MyUser userInfo = BmobUser.getCurrentUser(Global.mContext, MyUser.class);

    /**
     * 单例化数据库
     */
    private DBManager(Context context) {
        DBHelper dbHelper = new DBHelper(context, DB_NAME, null, VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * 获取实例
     */
    public synchronized static DBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }


    /**
     * 插入一个用户
     * @param myUser
     * @return
     */
    @Override
    public synchronized Observable<Boolean> setUserInfo(final MyUser myUser) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (myUser != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("u_email", myUser.getEmail());
                    contentValues.put("u_phone", myUser.getMobilePhoneNumber());
                    contentValues.put("u_id", myUser.getObjectId());
                    if (sqLiteDatabase.insert("user", null, contentValues) != -1) {
                        subscriber.onNext(true);
                    } else {
                        subscriber.onNext(false);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 保存快递结果列表
     * @param result 结果
     * @param list   列表
     * @return    observable
     */
    @Override
    public Observable<Boolean> saveExpResultList(final Result result, final List<Lists> list) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (list != null){
                    for (Lists mLists :
                            list) {
                        mLists.setExpressId(result.getNo());
                        mLists.setCompany(result.getCompany());
                        mLists.setUserId(userInfo.getObjectId());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("u_id",mLists.getUserId());
                        contentValues.put("e_express_id",mLists.getExpressId());
                        contentValues.put("e_company",mLists.getCompany());
                        contentValues.put("e_address",mLists.getRemark());
                        contentValues.put("e_update_time",mLists.getDatetime());
                        if (sqLiteDatabase.insert("express", null, contentValues) != -1) {
                            subscriber.onNext(true);
                        } else {
                            subscriber.onNext(false);
                        }
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 检查是否有更新
     * @param no  快递单号
     * @param list  列表
     * @return    ob
     */
    @Override
    public Observable<Integer> doseHaveUpdate(final String no, final List<Lists> list) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int size = list.size();
                Log.e("update", "size db "+size+"  no  "+ no);
                Log.e("update", "size db size "+listSize(no));
                if (size > listSize(no)){
                    subscriber.onNext(listSize(no));
                }else {
                    subscriber.onNext(size);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 快递追踪
     * @param traceExpress 快递追踪
     * @return ob
     */
    @Override
    public Observable<Boolean> traceExpress(final TraceExpress traceExpress) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (traceExpress != null){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("u_id",traceExpress.getUser());
                    contentValues.put("e_express_id",traceExpress.getExpressId());
                    sqLiteDatabase.insert("trace",null,contentValues);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 检查是否追踪
     * @param no 运单
     * @return  ob
     */
    @Override
    public Observable<Boolean> isTraceExpress(final String no) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                int count = 0;
                Cursor cursor = sqLiteDatabase.query("trace",null,"e_express_id = ?",new String[]{no},null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        count++;
                    }while (cursor.moveToNext());
                }
                cursor.close();
                if (count > 0){
                    subscriber.onNext(true);
                }else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 更新追踪单号的名称
     * @param title   名称
     * @param no      单号
     * @return     op
     */
    @Override
    public Observable<Boolean> updateTraceTitle(final String title, final String no) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                ContentValues values = new ContentValues();
                values.put("t_title",title);
                sqLiteDatabase.update("trace",values,"e_express_id = ?",new String[]{no});
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }


    /**
     * 数据的数量
     * @param no 运单号
     * @return   结果数
     */
    public int listSize(String no){
        int size = 0;
        Cursor cursor = sqLiteDatabase.query("express",null,"e_express_id = ?",new String[]{no},null,null,null);
        if (cursor.moveToFirst()){
            do {
                size++;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return size;
    }
}
