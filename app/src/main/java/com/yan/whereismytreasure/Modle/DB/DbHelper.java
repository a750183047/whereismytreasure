package com.yan.whereismytreasure.Modle.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by a7501 on 2016/6/1.
 */
public class DBHelper extends SQLiteOpenHelper{

    /**
     * 账号表创建语句 user
     * **/
    public static final String CREATE_USER = "create table user ("   //
            +"id integer primary key autoincrement,"                       //自增主键
            +"u_email text,"                                               // 用户邮箱
            +"u_phone text,"                                               // 用户手机号
            +"u_id    text"                                                //用户id
            +")";

    /**
     *
     * **/
    public static final String CREATE_EXPRESS = "create table express ("   //
            +"id integer primary key autoincrement,"                       //自增主键
            +"u_id          text,"                                               // 用户id
            +"e_update_time text,"                                               // 更新 时间
            +"e_address     text,"                                                //信息
            +"e_company     text,"
            +"e_express_id  text"
            +")";

    private static final String CREATE_TRACE = "create table trace ("   //
            +"id integer primary key autoincrement,"                       //自增主键
            +"u_id          text,"                                               // 用户id
            +"e_express_id  text,"
            +"t_title       text"                                      //名称
            +")";

    /**
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_EXPRESS);
        db.execSQL(CREATE_TRACE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){

        }
    }
}
