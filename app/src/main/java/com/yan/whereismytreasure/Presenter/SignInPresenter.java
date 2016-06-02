package com.yan.whereismytreasure.Presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.Modle.DB.DBManager;
import com.yan.whereismytreasure.UI.ViewInterface.SignInInterface;

import cn.bmob.v3.listener.SaveListener;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 登录
 * Created by a7501 on 2016/5/26.
 */
public class SignInPresenter extends MvpBasePresenter<SignInInterface> {

    public void signIn(String account, String password) {
        getView().showProgressBar();
        final MyUser myUser = new MyUser();
        myUser.setUsername(account);
        myUser.setPassword(password);
        myUser.login(Global.mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                getView().dismissProgressBar();
                getView().onSignUnOk();
                insertUserToDB(myUser);

            }

            @Override
            public void onFailure(int i, String s) {
                getView().dismissProgressBar();
                getView().showError(s);
            }
        });
    }


    /**
     * 向数据库中插入用户信息
     *
     * @param myUser
     */
    public void insertUserToDB(MyUser myUser) {
        DBManager.getInstance(Global.mContext)
                .setUserInfo(myUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e("SignUp", "db insert " + aBoolean);
                    }
                });
    }
}
