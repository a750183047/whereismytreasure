package com.yan.whereismytreasure.UI.ViewActivity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yan.whereismytreasure.App.Global;
import com.yan.whereismytreasure.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);
                startActivity(new Intent(SplashActivity.this,FirstPageActivity.class));
            }
        }.start();
    }
}
