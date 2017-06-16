package com.ff.pp.cniao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.view.ErasableEditText;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivityFirst extends AppCompatActivity {
    private static final String TAG = "RegisterActivityFirst";
    private ErasableEditText mPhoneNumberEdit, mPasswordEdit;
    private EventHandler mEventHandler;
    private String phoneNumber;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "1cf1b9a5881b1", "d15cf1c8110bd3a0d9d7a0ccf4902430");
        setContentView(R.layout.activity_register_first);
        initToolbar();
        initView();
        initSmsSend();
    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar) findViewById(R.id.toolbar_register);
//        toolbar.setCenterIcon(R.mipmap.ic_launcher);
        toolbar.setTitle("用户注册");
        toolbar.setRightButtonIcon(R.drawable.ic_menu_forward);
        toolbar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberAndPasswordIsInvalid()) return;
                getPhoneNumberAndPassword();
                requestVerificationCode();

            }
        });
    }

    private void initView() {
        mPhoneNumberEdit = (ErasableEditText) findViewById(R.id.editTextClear_user_name);
        mPasswordEdit = (ErasableEditText) findViewById(R.id.editTextClear_password);
    }

    private void getPhoneNumberAndPassword() {
        phoneNumber = mPhoneNumberEdit.getText().toString();
        password = mPasswordEdit.getText().toString();
    }


    private void initSmsSend() {
        SMSSDK.getSupportedCountries();
        mEventHandler = new EventHandler() {

            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                //获取验证码成功
                                RegisterActivitySecond.startActivity(RegisterActivityFirst.this,
                                        phoneNumber, password);
                                finish();
                                Log.e(TAG, "afterEvent: 获取验证码成功");
                            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                                //返回支持发送验证码的国家列表

                                List<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) data;
                                for (Map.Entry<String, Object> entry : list.get(0).entrySet()) {
                                    Log.e(TAG, "entry.getKey(): " + entry.getKey() +
                                            "entry.getValue: " + entry.getValue().toString());
                                }
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

    private void requestVerificationCode() {
        String[] countries = SMSSDK.getCountry("42");
        SMSSDK.getVerificationCode(countries[1], phoneNumber);
    }

    private boolean phoneNumberAndPasswordIsInvalid() {
        String content = null;
        content = mPhoneNumberEdit.getEditableText().toString();
        if (TextUtils.isEmpty(content)) {
            T.showTips("用户名不能为空");
            return true;
        }

        if (content.trim().length() != 11) {
            T.showTips("手机号码长度错误.");
            return true;
        }

        String rule = "^1(3|4|5|7|8)[0-9]\\d{8}$";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(content);
        if (!matcher.matches()) {
            T.showTips("手机号码格式错误.");
            return true;
        }

        content = mPasswordEdit.getEditableText().toString();
        if (TextUtils.isEmpty(content)) {
            T.showTips("密码不能为空");
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
