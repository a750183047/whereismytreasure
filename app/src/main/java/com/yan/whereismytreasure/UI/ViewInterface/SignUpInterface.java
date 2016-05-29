package com.yan.whereismytreasure.UI.ViewInterface;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Sign Up
 * Created by a7501 on 2016/5/28.
 */
public interface SignUpInterface extends MvpView {
    void showError(String s);
    void onSignUpOk();
    void showProgressBar();
    void dismissProgressBar();
}
