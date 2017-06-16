package com.ff.pp.cniao;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.view.CountDownTimerView;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;

import cn.smssdk.SMSSDK;

import cn.smssdk.EventHandler;

public class RegisterActivitySecond extends AppCompatActivity {
    private static final String TAG = "RegisterActivitySecond";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String PASSWORD = "PASSWORD";

    private EventHandler mEventHandler;

    private String password, phoneNumber;
    private TextView tips;
    private EditText verificationCodeEditText;
    private Button getCodeAgainBtn;
    private CountDownTimerView countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_second);
        initSMSSDK();
        initToolbar();
        initView();
        initPhoneNumberAndPassword();
        initSmsSend();
        setTips();
        countDownTimer.start();

    }

    private void initSMSSDK() {

        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String apiKey = appInfo.metaData.getString("SMSAPI_KEY");
            String secret = appInfo.metaData.getString("SMSAPI_SECRET");
            SMSSDK.initSDK(this, apiKey, secret);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setTips() {
        tips.setText("己将验证码发到您的号码为" + phoneNumber + "的手机。");
    }

    private void initPhoneNumberAndPassword() {
        password = getIntent().getStringExtra(PASSWORD);
        phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar)
                findViewById(R.id.toolbar_register_second);

        toolbar.setTitle("输入验证码");
        toolbar.setRightButtonIcon(R.drawable.ic_cab_done_holo_dark);
        toolbar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("+86", phoneNumber,
                        verificationCodeEditText.getText().toString());
            }
        });
    }

    private void initView() {
        tips = (TextView) findViewById(R.id.text_tips);
        verificationCodeEditText = (EditText) findViewById(R.id.editTextClear_verification_code);
        getCodeAgainBtn = (Button) findViewById(R.id.btn_get_code_again);
        countDownTimer = new CountDownTimerView(getCodeAgainBtn);
    }

    public void getVerificationCodeAgain(View view) {
        String[] countries = cn.smssdk.SMSSDK.getCountry("42");
        SMSSDK.getVerificationCode(countries[1], phoneNumber);
    }

    private void initSmsSend() {
        SMSSDK.getSupportedCountries();
        mEventHandler = new EventHandler() {

            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == cn.smssdk.SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == cn.smssdk.SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                                Log.e(TAG, "afterEvent: ");
                                startActivity(new Intent(RegisterActivitySecond.this, MainActivity.class));
                                finish();
                            } else if (event == cn.smssdk.SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                //获取验证码成功
                                countDownTimer.start();
                                T.showTips("已再次发送验证码");
                            }
                        } else {
                            ((Throwable) data).printStackTrace();
                        }
                    }
                });

            }
        };
        SMSSDK.registerEventHandler(mEventHandler); //注册短信回调
    }


    public static void startActivity(Context context, String phoneNumber, String password) {
        Intent intent = new Intent(context, RegisterActivitySecond.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        intent.putExtra(PASSWORD, password);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
