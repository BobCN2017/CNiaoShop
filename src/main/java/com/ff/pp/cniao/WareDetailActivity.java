package com.ff.pp.cniao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.bean.BaseMessage;
import com.ff.pp.cniao.bean.Order;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.tools.WareInCartProvider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;
import okhttp3.Response;

public class WareDetailActivity extends BaseActivity {

    private static final String TAG = "WareDetailActivity";
    private static final String KEY_WARE = "key_ware";
    private WebView mWebView;
    private Ware mWare;
    private WebInterface mWebInterface;

    private WareInCartProvider mWareInCartProvider;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        mWare = getWare();

        initToolbar();
        initWebView();

        mWareInCartProvider = new WareInCartProvider();
        mDialog = new SpotsDialog(this, "Loading...");
        mDialog.show();

        ShareSDK.initSDK(this, "1cd066f3861f0");
    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar) findViewById(R.id.toolbar_ware_detail);

        toolbar.setTitle("商品详情");
        toolbar.setRightButtonIcon(R.drawable.ssdk_oks_classic_twitter);

        toolbar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webView_ware_detail);

        Log.e(TAG, "initWebView: " + mWebView.toString());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setAppCacheEnabled(true);

//        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
//        settings.setDefaultTextEncodingName("utf-8");
//        settings.setLoadsImagesAutomatically(true);


        mWebInterface = new WebInterface(this);
        mWebView.addJavascriptInterface(mWebInterface, "appInterface");
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl(Constants.CAMPAIGN_WARE_DETAIL);
    }


    public static void startThisActivity(Context source, Ware ware) {
        Intent intent = new Intent(source, WareDetailActivity.class);
        intent.putExtra(KEY_WARE, ware);
        source.startActivity(intent);
    }

    public Ware getWare() {
        Serializable extra = getIntent().getSerializableExtra(KEY_WARE);
        if (extra == null)
            return null;
        return (Ware) extra;
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(mWare.getName());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(mWare.getImgUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWare.getName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(mWare.getImgUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    class WebInterface {
        private Context context;

        public WebInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void addToCart(long id) {
            addFavoriteToServer(mWare);

            T.showTips("己放入购物车,实际为加入收藏夹");
        }

        @android.webkit.JavascriptInterface
        public void buy(long id) {
            mWareInCartProvider.put(mWare);

            T.showTips("去付款，实际为加入购物车");
        }

        public void showDetail(final long id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail('" + id + "')");
                }
            });
        }

    }

    private void addFavoriteToServer(Ware ware) {

        int userId = MyApplication.getInstance().getUser().getUserInfo().getId();

        String url = Constants.FAVORITE_CREATE_URL;
        Map<String, String> params = new HashMap<>(2);
        params.put("user_id", userId + "");
        params.put("ware_id", ware.getId() + "");

        OkHttpHelper.getInstance().post(url, params, new SpotsDialogCallBack<BaseMessage>(this) {
            @Override
            public void onSuccess(Response response, BaseMessage message) {

                if (message.getStatus() == 1) {
                    Log.e(TAG, "onSuccess 加入收藏成功: " + message.getMessage());
                } else {
                    Log.e(TAG, "onSuccess 加入收藏失败: " + message.getMessage());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
            mWebInterface.showDetail(mWare.getId());

        }
    }


}
