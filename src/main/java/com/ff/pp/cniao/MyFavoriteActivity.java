package com.ff.pp.cniao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.adapter.FavoriteWareAdapter;
import com.ff.pp.cniao.adapter.OrderAdapter;
import com.ff.pp.cniao.bean.FavoriteWare;
import com.ff.pp.cniao.bean.Order;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MyFavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FavoriteWareAdapter mAdapter;
    private List<FavoriteWare> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        initRecyclerView();
        requestDataFromServer();
    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_favorite_list);
        mData = new ArrayList<>();
        mAdapter = new FavoriteWareAdapter(this, mData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    private void requestDataFromServer() {
        int userId = MyApplication.getInstance().getUser().getUserInfo().getId();

        String url = Constants.FAVORITE_LIST_URL;
        url+="?user_id="+ userId;

        OkHttpHelper.getInstance().get(url, new SpotsDialogCallBack<List<FavoriteWare>>(this) {
            @Override
            public void onSuccess(Response response, List<FavoriteWare> wares) {

                loadDataToList(wares);

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void loadDataToList(List<FavoriteWare> wares) {
        mData.clear();
        mData.addAll(wares);
        mAdapter.notifyDataSetChanged();
    }
}
