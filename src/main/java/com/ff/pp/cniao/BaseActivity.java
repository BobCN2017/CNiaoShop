package com.ff.pp.cniao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.myapplication2.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void startActivityAfterCheckLogin(Intent intent){
        if (MyApplication.getInstance().getToken()==null){
            Intent loginIntent=new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            MyApplication.getInstance().putFinalIntent(intent);
        }else {
            startActivity(intent);
        }
    }
}
