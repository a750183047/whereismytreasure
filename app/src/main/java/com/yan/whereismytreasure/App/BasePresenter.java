package com.yan.whereismytreasure.App;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * BasePresenter
 * Created by a7501 on 2016/5/22.
 */
public class BasePresenter<V extends MvpView> extends MvpBasePresenter{

    public BasePresenter() {
        super();
    }
}
