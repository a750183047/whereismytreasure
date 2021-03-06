package com.yan.whereismytreasure.Presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.Modle.DB.DBManager;
import com.yan.whereismytreasure.UI.ViewInterface.SignUpInterface;

import cn.bmob.v3.listener.SaveListener;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * sign up 注册
 * Created by a7501 on 2016/5/28.
 */
public class SignUpPresenter extends MvpBasePresenter<SignUpInterface> {
    public void signUp(String account, String password, String phone) {

        getView().showProgressBar();
        final MyUser myUser = new MyUser();
        myUser.setUsername(account);
        myUser.setEmail(account);
        myUser.setPassword(password);
        myUser.setMobilePhoneNumber(phone);
        Log.e("SignUpP", myUser.toString());
        myUser.signUp(Global.mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                getView().dismissProgressBar();
                getView().onSignUpOk();
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
