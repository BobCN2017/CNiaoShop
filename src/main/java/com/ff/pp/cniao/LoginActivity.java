package com.ff.pp.cniao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.bean.User;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.DESUtil;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.cniao.view.ErasableEditText;
import com.ff.pp.myapplication2.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private ErasableEditText mUsernameEdit, mPasswordEdit;
    private TextView mRegister,mForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setUserRegisterAndForgetPasswordListener();
    }

    private void initView() {
        mUsernameEdit = (ErasableEditText) findViewById(R.id.editTextClear_user_name);
        mPasswordEdit = (ErasableEditText) findViewById(R.id.editTextClear_password);
        mRegister= (TextView) findViewById(R.id.text_register);
        mForgetPassword= (TextView) findViewById(R.id.text_forget_password);

    }

    private void setUserRegisterAndForgetPasswordListener() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivityFirst.class));
            }
        });

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void login(View view) throws UnsupportedEncodingException {

        if (usernameAndPasswordIsEmpty()) return;

        Map<String, String> params = new HashMap<>(2);
        params.put("phone", mUsernameEdit.getText().toString());
        params.put("password", DESUtil.encode(Constants.DES_KEY,
                mPasswordEdit.getText().toString()));

        OkHttpHelper.getInstance().post(Constants.USER_LOGIN, params,
                new SpotsDialogCallBack<User>(LoginActivity.this) {
                    @Override
                    public void onSuccess(Response response, User user) {
                        if (user.getStatus() == User.STATE_LOGIN_SUCCESS) {
                            MyApplication.getInstance().putUserAndToken(user);

                            if (MyApplication.getInstance().getFinalIntent()!=null){
                                MyApplication.getInstance().startActivityOfFinalIntent(
                                        LoginActivity.this);
                            }

                            T.showTips("登录成功.");
                            LoginActivity.this.setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }else {
                            T.showTips(user.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });

    }

    private boolean usernameAndPasswordIsEmpty() {
        String content = null;
        content = mUsernameEdit.getEditableText().toString();
        if (TextUtils.isEmpty(content)) {
            T.showTips("用户名不能为空");
            return true;
        }

        content = mPasswordEdit.getEditableText().toString();
        if (TextUtils.isEmpty(content)) {
            T.showTips("密码不能为空");
            return true;
        }
        return false;
    }
}
