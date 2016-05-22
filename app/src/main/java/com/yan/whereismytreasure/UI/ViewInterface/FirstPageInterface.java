package com.yan.whereismytreasure.UI.ViewInterface;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 首页View接口
 * Created by a7501 on 2016/5/22.
 */
public interface FirstPageInterface extends MvpView {
    void showToast(String s);
    void showList();
    void noResult();
}
