package com.yan.whereismytreasure.UI.ViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.Presenter.MainPresenter;
import com.yan.whereismytreasure.R;
import com.yan.whereismytreasure.UI.ViewInterface.MainInterface;

import cn.bmob.v3.BmobUser;

public class MainActivity extends MvpActivity<MainInterface,MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener,MainInterface {

    private Spinner mKuaidi;
    private String[] KUAIDI;
    private String[] KuaiDiDaiMa;
    private Button mDenglu;
    private ListView mJieguo;
    private TextView mWujieguo;
    private ProgressBar mProgressBar;
    private EditText mYundanNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("快递查询");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mKuaidi = (Spinner) findViewById(R.id.spinner);
        mYundanNumber = (EditText) findViewById(R.id.edit_yundan);
        mJieguo = (ListView) findViewById(R.id.list_first_page);
        mDenglu = (Button) findViewById(R.id.textView);
        mWujieguo = (TextView) findViewById(R.id.text_wujieguo);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        KuaiDiDaiMa = getResources().getStringArray(R.array.kuaididaima);

        initSpinner();
        initEditText();

        mDenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("还在开发中呢。。");
            }
        });
    }

    /***
     * 从SP中获取到上次查询的值
     */
    private void initEditText() {
        if (getPresenter().getNumberFromSP(Global.mContext) != null){
            mYundanNumber.setText(getPresenter().getNumberFromSP(Global.mContext));
        }
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
                            getPresenter().saveToSP(Global.mContext,no);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_sign_out:
                BmobUser.logOut(Global.mContext);   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(Global.mContext); // 现在的currentUser是null了
                startActivity(new Intent(MainActivity.this,FirstPageActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.page_home:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
