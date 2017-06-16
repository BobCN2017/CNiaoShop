package com.ff.pp.cniao;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.adapter.WareAdapterInPay;
import com.ff.pp.cniao.bean.BaseMessage;
import com.ff.pp.cniao.bean.PayMessage;
import com.ff.pp.cniao.bean.WareInCart;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.GsonUtil;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.tools.WareInCartProvider;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;
import com.pingplusplus.android.Pingpp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class PayChanelActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PayChanelActivity";
    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";

    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private static final int ORDER_STATUS_SUCCESS = 1;
    private static final int ORDER_STATUS_FAIL=-1;
    private static final int ORDER_STATUS_CANCEL=-2;

    private String mPayChannel = CHANNEL_ALIPAY;

    private RelativeLayout mAlipayLayout, mWeixinPayLayout, mYinLianPayLayout;
    private RadioButton mAlipayRadioButton, mWeixinPayRadioButton, mYinLianRadioButton;

    private Map<String, RadioButton> mRadioMap;

    private RecyclerView mRecyclerView;
    private List<WareInCart> mData;
    private WareAdapterInPay mAdapter;

    private WareInCartProvider mProvider;

    private TextView mTextTotalCost;
    private int mAmount;
    private String mOrderNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_chanel);
        initToolbar();
        initRecyclerView();
        initOtherView();
        initData();
        initBottomView();
    }

    private void initBottomView() {
        mTextTotalCost = (TextView) findViewById(R.id.textView_total_pay);
        mTextTotalCost.setText("总价：¥ " + mAmount);
    }

    private void initData() {
        mRadioMap = new HashMap<>(3);
        mRadioMap.put(CHANNEL_ALIPAY, mAlipayRadioButton);
        mRadioMap.put(CHANNEL_WECHAT, mWeixinPayRadioButton);
        mRadioMap.put(CHANNEL_UPACP, mYinLianRadioButton);

        mAmount = (int)(mAdapter.getWaresTotalCost());
        if (!TextUtils.isEmpty(mPayChannel)){
            setPayChannelSelected();
        }
    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar) findViewById(R.id.toolbar_pay_channel);
        toolbar.setTitle("支付订单");
        toolbar.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void initRecyclerView() {
        mProvider = new WareInCartProvider();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_order_list);
        mData = mProvider.getAllSelected();
        mAdapter = new WareAdapterInPay(this, mData);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);

    }


    private void initOtherView() {
        mAlipayLayout = (RelativeLayout) findViewById(R.id.relativeLayout_alipay);
        mWeixinPayLayout = (RelativeLayout) findViewById(R.id.relativeLayout_wxpay);
        mYinLianPayLayout = (RelativeLayout) findViewById(R.id.relativeLayout_yinlianpay);

        mAlipayRadioButton = (RadioButton) findViewById(R.id.radiobutton_alipay);
        mWeixinPayRadioButton = (RadioButton) findViewById(R.id.radiobutton_wxpay);
        mYinLianRadioButton = (RadioButton) findViewById(R.id.radiobutton_yinlianpay);

        mAlipayLayout.setOnClickListener(this);
        mWeixinPayLayout.setOnClickListener(this);
        mYinLianPayLayout.setOnClickListener(this);

    }

    private void setPayChannelSelected() {
        mRadioMap.get(mPayChannel).setChecked(true);
    }

    @Override
    public void onClick(View v) {
        for (Map.Entry<String, RadioButton> entry : mRadioMap.entrySet()) {
            if (entry.getKey().equals(v.getTag())) {
                entry.getValue().setChecked(!entry.getValue().isChecked());
                if (entry.getValue().isChecked()) {
                    mPayChannel = entry.getKey();
                }else {
                    mPayChannel="";
                }
            } else {
                entry.getValue().setChecked(false);
            }
        }
    }

    public void submitOrder(View view) {

        if (TextUtils.isEmpty(mPayChannel)){
            T.showTips("必须选择一个支付方式");
            return;
        }
        requestChargeInfo(view);

    }

    private void requestChargeInfo(final View view) {
        view.setEnabled(false);

        OkHttpHelper.getInstance().post(Constants.ORDER_CREATE_URL, getPostParams(),
                new SpotsDialogCallBack<PayMessage>(this) {
                    @Override
                    public void onSuccess(Response response, PayMessage payMessage) {
                        view.setEnabled(true);
                        if ("success".equals(payMessage.getMessage())){
                            mOrderNum = payMessage.getData().getOrderNum();
                            PayMessage.OrderData orderData = payMessage.getData();
                            String dataStr=GsonUtil.gson.toJson(orderData.getCharge());
                            Pingpp.createPayment(PayChanelActivity.this, dataStr);
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {
                        view.setEnabled(true);
                        if (e != null)
                            e.printStackTrace();
                    }
                });


    }

    @NonNull
    private Map<String, String> getPostParams() {
        Map<String, String> params = new HashMap<>();
        String json = GsonUtil.gson.toJson(getOrderWareList());
        params.put("user_id", "" + MyApplication.getInstance().getUser().getUserInfo().getId());
        params.put("item_json", json);
        params.put("amount", "" + mAmount);
        params.put("addr_id", "1");
        params.put("pay_channel", mPayChannel);

        return params;
    }

    private List<OrderWare> getOrderWareList() {
        List<OrderWare> list = new ArrayList<>();
        for (WareInCart ware : mData) {
            OrderWare orderWare = new OrderWare(ware.getId(), ware.getCount());
            list.add(orderWare);
        }
        return list;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
            switch (result){
                case "success":
                    T.showTips("支付成功");
                    changeOrderStatusOfNet(ORDER_STATUS_SUCCESS);
                    removeWarePayedInCart();
                    backToCartFragmentAfterPayed();
                    break;
                case "fail":
                    changeOrderStatusOfNet(ORDER_STATUS_FAIL);
                    T.showTips("支付失败");
                    break;
                case "cancel":
                    changeOrderStatusOfNet(ORDER_STATUS_CANCEL);
                    T.showTips("支付已取消");
                    break;
                case "invalid":
                    T.showTips("支付插件未安装");
                    break;
                case "unknown":
                    T.showTips("发生未知错误");
                    break;

            }
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                T.showTips(result+errorMsg+extraMsg);
            }
        }
    }

    private void changeOrderStatusOfNet(int status) {
        Map<String, String> params = new HashMap<>();
        params.put("order_num",mOrderNum);
        params.put("status", status+"");

        OkHttpHelper.getInstance().post(Constants.ORDER_COMPLETE_URL, params,
                new SpotsDialogCallBack<BaseMessage>(this) {
                    @Override
                    public void onSuccess(Response response, BaseMessage message) {
                        if (message.getStatus()==1){
                            Log.e(TAG, "onSuccess: 订单状态更新成功");
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void removeWarePayedInCart() {
        Log.e(TAG, "removeWarePayedInCart: "+mProvider.toString() );
        mProvider.removeWares(mData);
    }

    private void backToCartFragmentAfterPayed() {
        Intent intent=new Intent();
        intent.putExtra(Constants.KEY_WARE_PAYED_LIST,(Serializable) mData);
        setResult(RESULT_OK,intent);
        finish();
    }

    class OrderWare {

        private int ware_id;
        private int amount;

        public OrderWare(int ware_id, int amount) {
            this.ware_id = ware_id;
            this.amount = amount;
        }

        public int getWareId() {
            return ware_id;
        }

        public void setWareId(int ware_id) {
            this.ware_id = ware_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
