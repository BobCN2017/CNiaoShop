package com.ff.pp.cniao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ff.pp.cniao.AddUserAddressActivity;
import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.LoginActivity;
import com.ff.pp.cniao.MyFavoriteActivity;
import com.ff.pp.cniao.MyOrderActivity;
import com.ff.pp.cniao.adapter.FavoriteWareAdapter;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.User;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by PP on 2017/3/20.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";
    private static final int REQUEST_CODE_LOGIN = 1;
    private static final int REQUEST_CODE_ADDRESS = 2;
    private CircleImageView mAvatar;
    private TextView mUserName;

    private TextView mUserOrder, mUserFavorite, mAddressees, mUserLove;

    private Button mBtn_exit_login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        initView(view);
        initEvents();
        return view;
    }

    private void initView(View view) {
        initFourTextView(view);
        initOthers(view);
    }


    private void initFourTextView(View view) {
        mUserOrder = (TextView) view.findViewById(R.id.text_user_order);
        mUserFavorite = (TextView) view.findViewById(R.id.text_user_favorite);
        mAddressees = (TextView) view.findViewById(R.id.text_addressees);
        mUserLove = (TextView) view.findViewById(R.id.text_user_love);

    }

    private void initOthers(View view) {
        mAvatar = (CircleImageView) view.findViewById(R.id.circleImageView_avatar);
        mUserName = (TextView) view.findViewById(R.id.text_user_name);
        mBtn_exit_login = (Button) view.findViewById(R.id.button_exit_login);

        if (isNotLogined()) {
            hideExitLoginButton();
            resetShowUserInfo();
        } else {
//            Log.e(TAG, "initView: getToken() != null");
            showExitLoginButton();
            showUserInfo(MyApplication.getInstance().getUser());
        }
    }

    private void initEvents() {
        mUserOrder.setOnClickListener(this);
        mUserFavorite.setOnClickListener(this);
        mAddressees.setOnClickListener(this);
        mUserLove.setOnClickListener(this);

        mAvatar.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mBtn_exit_login.setOnClickListener(this);
    }

    private boolean isNotLogined() {
        if (MyApplication.getInstance().getUser() == null) return true;
        return false;
    }

    private void hideExitLoginButton() {
        mBtn_exit_login.setVisibility(View.GONE);
    }

    private void resetShowUserInfo() {
        if (mUserName != null)
            mUserName.setText("用户登录");

        if (mAvatar != null)
            mAvatar.setImageResource(R.drawable.ssdk_oks_classic_twitter);
    }

    private void showExitLoginButton() {
        mBtn_exit_login.setVisibility(View.VISIBLE);
    }

    private void showUserInfo(User user) {

        if (user != null && user.getUserInfo() != null) {
            User.UserInfo userInfo = user.getUserInfo();
            if (mUserName != null)
                mUserName.setText(userInfo.getUsername());

            if (mAvatar != null)
                Glide.with(getContext())
                        .load(userInfo.getLogo_url()).asBitmap()
                        .dontAnimate().
                        into(mAvatar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circleImageView_avatar:
            case R.id.text_user_name:
                toLoginActivity();
                return;
            case R.id.button_exit_login:
                MyApplication.getInstance().clearUserAndToken();
                hideExitLoginButton();
                resetShowUserInfo();
                return;
        }
        if (isNotLogined()) {
            processNeedToLogin();
            return;
        }
        switch (v.getId()) {
            case R.id.text_user_order:
                toMyOrderActivity();
                break;
            case R.id.text_user_favorite:
                toFavoriteWareActivity();
                break;
            case R.id.text_addressees:
                toAddUserAddressActivity();
                break;
            case R.id.text_user_love:
                //TODO
                break;
        }

    }

    private void processNeedToLogin() {
        T.showTips("请先登录！");
        toLoginActivity();
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }


    private void toMyOrderActivity() {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        startActivity(intent);
    }


    private void toAddUserAddressActivity() {
        Intent intent = new Intent(getContext(), AddUserAddressActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADDRESS);
    }

    private void toFavoriteWareActivity() {
        Intent intent = new Intent(getActivity(), MyFavoriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            User user = MyApplication.getInstance().getUser();
            if (user != null) {
                showUserInfo(user);
                showExitLoginButton();
            }
        }
    }
}
