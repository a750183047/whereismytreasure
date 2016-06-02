package com.yan.whereismytreasure.UI.ViewActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Modle.Bean.UserBean.MyUser;
import com.yan.whereismytreasure.Presenter.FirstPagePresenter;
import com.yan.whereismytreasure.Presenter.SignInPresenter;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewInterface.SignInInterface;
import com.yan.whereismytreasure.Utils.ShowProgressBar;

public class SignInActivity extends MvpActivity<SignInInterface,SignInPresenter>implements SignInInterface, View.OnClickListener,TextWatcher {

    private EditText mAccount;
    private EditText mPassword;
    private TextView mSignUp;
    private Button mSingIn;
    private ShowProgressBar showProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_acitvity);
        initView();

    }

    private void initView() {
        initToolBar();
        mAccount = (EditText) findViewById(R.id.edit_account);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mSingIn = (Button) findViewById(R.id.button_sign_in);
        assert mSingIn != null;
        mSingIn.setOnClickListener(this);
        mSignUp = (TextView) findViewById(R.id.text_sign_up);
        showProgressBar = new ShowProgressBar();

        mAccount.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);

        mSignUp.setOnClickListener(this);

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
    public void showError(String s) {
        Global.showToast(s);

    }

    @Override
    public void onSignUnOk() {
        startActivity(new Intent(SignInActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void showProgressBar() {
        showProgressBar.showProgressDialog(SignInActivity.this,"登录中");
    }

    @Override
    public void dismissProgressBar() {
        showProgressBar.dismissProgressDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in:
                getPresenter().signIn(mAccount.getText().toString(),
                        mPassword.getText().toString());

                break;
            case R.id.text_sign_up:
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mAccount.getText().toString())|TextUtils.isEmpty(mPassword.getText().toString())){
            mSingIn.setEnabled(false);
            Log.e("SignIn","false");
        }else {
            mSingIn.setEnabled(true);
            Log.e("SignIn","true");
        }
    }
}
