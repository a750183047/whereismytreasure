package com.yan.whereismytreasure.UI.ViewActivity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private TextView mDenglu;
    private ListView mJieguo;
    private TextView mWujieguo;

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
        mJieguo = (ListView) findViewById(R.id.list_first_page);
        mDenglu = (TextView) findViewById(R.id.textView);
        mWujieguo = (TextView) findViewById(R.id.text_wujieguo);

        initSpinner();

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
                showToast(KUAIDI[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast(KUAIDI[0]);
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
        mWujieguo.setVisibility(View.GONE);
        mJieguo.setVisibility(View.VISIBLE);
    }

    @Override
    public void noResult() {
        mJieguo.setVisibility(View.GONE);
        mWujieguo.setVisibility(View.VISIBLE);
    }
}
