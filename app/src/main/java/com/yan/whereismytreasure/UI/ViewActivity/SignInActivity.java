package com.yan.whereismytreasure.UI.ViewActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.yan.whereismytreasure.Presenter.FirstPagePresenter;
import com.yan.whereismytreasure.Presenter.SignInPresenter;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewInterface.SignInInterface;

public class SignInActivity extends MvpActivity<SignInInterface,SignInPresenter>implements SignInInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_acitvity);
        initView();

    }

    private void initView() {
        initToolBar();

    }

    @NonNull
    @Override
    public SignInPresenter createPresenter() {
        return new SignInPresenter();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_tool_bar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("登录");
    }

    @Override
    public void showError() {

    }
}
