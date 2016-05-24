package com.yan.whereismytreasure.UI.ViewActivity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Presenter.FirstPagePresenter;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewInterface.FirstPageInterface;

public class FirstPageActivity extends MvpActivity<FirstPageInterface,FirstPagePresenter> implements FirstPageInterface {

    private Spinner mKuaidi;
    private String[] KUAIDI;
    private String[] KuaiDiDaiMa;
    private TextView mDenglu;
    private ListView mJieguo;
    private TextView mWujieguo;
    private ProgressBar mProgressBar;
    private EditText mYundanNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.activity_frist_page);
        mKuaidi = (Spinner) findViewById(R.id.spinner);
        mYundanNumber = (EditText) findViewById(R.id.edit_yundan);
        mJieguo = (ListView) findViewById(R.id.list_first_page);
        mDenglu = (TextView) findViewById(R.id.textView);
        mWujieguo = (TextView) findViewById(R.id.text_wujieguo);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        KuaiDiDaiMa = getResources().getStringArray(R.array.kuaididaima);


        initSpinner();

        mDenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("功能制作中。。。。");
            }
        });

    }

    /**
     *初始化Spinner
     */
    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_item);
        //资源文件
        KUAIDI = getResources().getStringArray(R.array.kuaidi);
        for (int i = 0; i < KUAIDI.length; i++) {
            adapter.add(KUAIDI[i]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKuaidi.setAdapter(adapter);

        mKuaidi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position !=0){
                    String no = mYundanNumber.getText().toString();
                    if (!no.isEmpty()){
                        if (!(no.length()<8)){
                            getPresenter().getInfo(KuaiDiDaiMa[position],no,mJieguo);
                        }else {
                            showToast("运单号好像有问题啊");
                        }
                    }else {
                        showToast("运单号是空的呢");
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @NonNull
    @Override
    public FirstPagePresenter createPresenter() {
        return new FirstPagePresenter();
    }


    @Override
    public void showToast(String s) {
        Global.showToast(s);
    }

    /**
     * 显示列表
     */
    @Override
    public void showList() {
        mProgressBar.setVisibility(View.GONE);
        mJieguo.setVisibility(View.VISIBLE);
    }

    @Override
    public void noResult() {
        mProgressBar.setVisibility(View.GONE);
        mWujieguo.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAdapter() {
        mJieguo.setAdapter(getPresenter().getAdapter());
    }

    @Override
    public void nowLoading() {
        mJieguo.setVisibility(View.GONE);
        mWujieguo.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        Log.e("First","noLoading");
    }
}
