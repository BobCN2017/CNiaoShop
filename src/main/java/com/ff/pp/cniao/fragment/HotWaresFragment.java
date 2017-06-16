package com.ff.pp.cniao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.ff.pp.cniao.WareDetailActivity;
import com.ff.pp.cniao.adapter.BaseAdapter;
import com.ff.pp.cniao.adapter.HotWareAdapter;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WaresPage;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.Page;
import com.ff.pp.cniao.view.DividerDecoration;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by PP on 2017/3/20.
 */

public class HotWaresFragment extends BaseFragment {

    private static final int REFRESH_DATA = 1;
    private static final int INITIAL_LOAD = 2;
    private static final int LOAD_MORE = 3;
    private RecyclerView mRecyclerView;
    private List<Ware> mData;
    private HotWareAdapter mAdapter;
    private MaterialRefreshLayout mRefreshLayout;
    private int mCurPage;
    private int mTotalPage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotwares, null);
        initView(view);
        Page page = Page.newBuilder()
                .setUrl(Constants.HOT_WARE_URL)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(new Page.OnPageListener() {
                    @Override
                    public void load(List list, int totalPage, int totalCount) {

                        initAdapterAndSetRecyclerViewAfterLoad(list);
                    }

                    @Override
                    public void refresh(List list, int totalPage, int totalCount) {
                        mData.clear();
                        mData.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void loadMore(List list, int totalPage, int totalCount) {
                        int position = mData.size();
                        mData.addAll(list);
                        mAdapter.notifyItemRangeChanged(position, mData.size());
                        mRecyclerView.scrollToPosition(position);
                    }
                }).build(getContext(), new TypeToken<WaresPage<Ware>>() {
                }.getType());

        page.request();
        return view;
    }

    private void initView(View view) {
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_hotwares);
    }

    private void initAdapterAndSetRecyclerViewAfterLoad(List list) {
        mData = list;
        mAdapter = new HotWareAdapter(getContext(), mData);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WareDetailActivity.startThisActivity(getContext(), mData.get(position));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 初始化和设置MaterialRefreshLayout。未封装Page前使用方法；
     *
     * @param view
     */
    private void initAndSetRefreshLayout(View view) {
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setProgressColors(new int[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED});
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                loadMoreData();
            }
        });
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        if ((mCurPage + 1) < mTotalPage) {
            getDataFromNet(++mCurPage, 10, LOAD_MORE);
        } else {
            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
            mRefreshLayout.finishRefreshLoadMore();
        }
    }

    private void refreshData() {
        mCurPage = 1;
        getDataFromNet(mCurPage, 10, REFRESH_DATA);

    }


    /**
     * 初始化RecyclerView及Adapter并设置；未封装Page前使用方法.
     *
     * @param view
     */
    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_hotwares);
        mData = new ArrayList<>();
        mCurPage = 1;
        getDataFromNet(mCurPage, 10, INITIAL_LOAD);
//        mAdapter = new HotWareAdapterOriginal(getContext(), mData);
        mAdapter = new HotWareAdapter(getContext(), mData);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了位置:" + position, Toast.LENGTH_SHORT).show();

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 请求网络数据。
     *
     * @param curPage
     * @param pageSize
     * @param state
     */
    private void getDataFromNet(int curPage, int pageSize, final int state) {
        Map<String, String> requestBody = new HashMap<>(2);
        requestBody.put("curPage", curPage + "");
        requestBody.put("pageSize", pageSize + "");

        OkHttpHelper.getInstance().post(Constants.HOT_WARE_URL, requestBody,
                new SpotsDialogCallBack<WaresPage<Ware>>(getContext()) {


                    @Override
                    public void onSuccess(Response response, WaresPage wares) {

                        if (state == REFRESH_DATA || state == INITIAL_LOAD) {
                            mData.clear();
                            mData.addAll(wares.getList());
                            mAdapter.notifyDataSetChanged();

                            mRefreshLayout.finishRefresh();
//                            mTotalPage = wares.getTotalPage();
                            mTotalPage = 4;
                        } else if (state == LOAD_MORE) {
                            int position = mData.size();
                            mData.addAll(wares.getList());
                            mAdapter.notifyItemRangeChanged(position, mData.size());
                            mRecyclerView.scrollToPosition(position);
                            mRefreshLayout.finishRefreshLoadMore();
                        }

                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }
}
