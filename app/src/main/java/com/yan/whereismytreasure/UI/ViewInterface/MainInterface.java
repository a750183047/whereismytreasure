package com.yan.whereismytreasure.UI.ViewInterface;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * main
 * Created by a7501 on 2016/5/28.
 */
public interface MainInterface extends MvpView {
    void showToast(String s);
    void showList();
    void noResult();
    void setAdapter();
    void nowLoading();
    void traceExpress();
    void isTraceExpress();
    void createDialog();
}
