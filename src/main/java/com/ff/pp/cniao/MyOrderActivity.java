package com.ff.pp.cniao;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ff.pp.cniao.Application.MyApplication;

import com.ff.pp.cniao.adapter.OrderAdapter;
import com.ff.pp.cniao.bean.Order;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MyOrderActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private static final String TAG = "MyOrderActivity";
    private static final int ORDER_TOTAL = 100;
    public static final int ORDER_STATUS_SUCCESS = 1;
    public static final int ORDER_STATUS_FAIL = -2;
    public static final int ORDER_STATUS_WAIT = 0;

    private TabLayout tabLayout;

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private List<Order> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initTabLayout();
        initRecyclerView();
        requestDataFromServer(ORDER_TOTAL);
    }



    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_order_list);
        mData = new ArrayList<>();
        mAdapter = new OrderAdapter(this, mData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }


    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_order);
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("全部");
        tab.setTag(ORDER_TOTAL);
        tabLayout.addTab(tab);

        TabLayout.Tab tabSuccess = tabLayout.newTab();
        tabSuccess.setText("支付成功");
        tabSuccess.setTag(ORDER_STATUS_SUCCESS);
        tabLayout.addTab(tabSuccess, 1);

        TabLayout.Tab tabWaitToPay = tabLayout.newTab();
        tabWaitToPay.setText("待支付");
        tabWaitToPay.setTag(ORDER_STATUS_WAIT);
        tabLayout.addTab(tabWaitToPay, 2);

        TabLayout.Tab tabPayFailed = tabLayout.newTab();
        tabPayFailed.setText("支付失败");
        tabPayFailed.setTag(ORDER_STATUS_FAIL);
        tabLayout.addTab(tabPayFailed, 3);
        tabLayout.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int orderStatus = (int) tab.getTag();
        requestDataFromServer(orderStatus);
    }

    private void requestDataFromServer(int orderStatus) {
        int userId = MyApplication.getInstance().getUser().getUserInfo().getId();

        String url = Constants.ORDER_LIST_URL;
        Map<String, String> params = new HashMap<>(2);
        params.put("user_id", userId + "");
        params.put("status", orderStatus + "");

        OkHttpHelper.getInstance().post(url, params, new SpotsDialogCallBack<List<Order>>(this) {
            @Override
            public void onSuccess(Response response, List<Order> orders) {

                loadDataToList(orders);

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void loadDataToList(List<Order> orders) {
        mData.clear();
        mData.addAll(orders);
        Collections.sort(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
