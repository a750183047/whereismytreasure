package com.yan.whereismytreasure.UI.ViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Presenter.SignUpPresenter;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewInterface.SignUpInterface;
import com.yan.whereismytreasure.Utils.ShowProgressBar;

/***
 * 注册逻辑
 */
public class SignUpActivity extends MvpActivity<SignUpInterface, SignUpPresenter> implements View.OnClickListener, SignUpInterface {

    private Toolbar idToolBar;
    private TextView toolbarTitle;
    private TextInputLayout textInputAccount;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputPasswordAgain;
    private Button mSignUp;
    private ShowProgressBar showProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /***
     * 初始化各个控件
     */
    private void initView() {
        setContentView(R.layout.activity_sign_up);
        idToolBar = (Toolbar) findViewById(R.id.id_tool_bar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        textInputAccount = (TextInputLayout) findViewById(R.id.text_input_account);
        textInputPassword = (TextInputLayout) findViewById(R.id.text_input_password);
        textInputPasswordAgain = (TextInputLayout) findViewById(R.id.text_input_password_again);
        mSignUp = (Button) findViewById(R.id.button_sign_up);
        mSignUp.setOnClickListener(this);
        showProgressBar = new ShowProgressBar();

        idToolBar.setTitle(getString(R.string.zhuce));
    }

    private EditText getEditAccount() {
        return (EditText) findViewById(R.id.edit_account);
    }

    private EditText getEditPassword() {
        return (EditText) findViewById(R.id.edit_password);
    }

    private EditText getEditPasswordAgain() {
        return (EditText) findViewById(R.id.edit_password_again);
    }

    private EditText getEditPhone() {
        return (EditText) findViewById(R.id.edit_phone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_up:
                if (getEditAccount().getText().toString().isEmpty()) {
                    textInputAccount.setError(getString(R.string.err_bunengweikong));
                } else {
                    textInputAccount.setErrorEnabled(false);
                }
                if (getEditPassword().getText().toString().isEmpty()) {
                    textInputPassword.setError(getString(R.string.err_mimabunengweikong));
                } else {
                    textInputPassword.setErrorEnabled(false);
                }
                if (!getEditPassword().getText().toString().equals(getEditPasswordAgain().getText().toString())) {
                    textInputPasswordAgain.setError(getString(R.string.err_liangcimimabuyizhi));
                } else {
                    textInputPasswordAgain.setErrorEnabled(false);
                    Log.e("SignUpP","signup");

                    getPresenter().signUp(getEditAccount().getText().toString(),
                            getEditPassword().getText().toString(),
                            getEditPhone().getText().toString());
                }
                break;
        }
    }

    @NonNull
    @Override
    public SignUpPresenter createPresenter() {
        return new SignUpPresenter();
    }


    @Override
    public void showError(String s) {
        Global.showToast(s);
    }

    @Override
    public void onSignUpOk() {
        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void showProgressBar() {
        showProgressBar.showProgressDialog(SignUpActivity.this,"注册中");
    }

    @Override
    public void dismissProgressBar() {
        showProgressBar.dismissProgressDialog();
    }
}
