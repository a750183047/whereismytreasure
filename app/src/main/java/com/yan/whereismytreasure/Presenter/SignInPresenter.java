package com.yan.whereismytreasure.Presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.UI.ViewInterface.SignInInterface;

import cn.bmob.v3.listener.SaveListener;

/**
 * 登录
 * Created by a7501 on 2016/5/26.
 */
public class SignInPresenter extends MvpBasePresenter<SignInInterface> {

    public void signIn(String account,String password){
        getView().showProgressBar();
        MyUser myUser = new MyUser();
        myUser.setUsername(account);
        myUser.setPassword(password);
        myUser.login(Global.mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                getView().dismissProgressBar();
                getView().onSignUnOk();
            }

            @Override
            public void onFailure(int i, String s) {
                getView().dismissProgressBar();
                getView().showError(s);
            }
        });
    }
}
