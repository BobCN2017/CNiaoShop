package com.ff.pp.cniao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.ff.pp.cniao.bean.WaresPage;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.adapter.BaseAdapter;
import com.ff.pp.cniao.adapter.HotWareAdapter;
import com.ff.pp.cniao.bean.Campaigns;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.Page;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.view.DividerDecoration;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class WareListActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "WareListActivity";
    private static final String COMPAIN_ID = "compain_id";
    private static final int TAG_DEFULT = 0;
    private static final int TAG_SALE_COUNT = 1;
    private static final int TAG_PRICE = 2;
    private static final int TAG_LISTVIEW = 100;
    private static final int TAG_GRIDVIEW = 101;

    private TabLayout tabLayout;
    private TextView textSummary;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private HotWareAdapter mAdapter;
    private List<Ware> mData;
    private Page mPage;

    private int mCampaignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);

        initTabLayout();
        initView();

        mCampaignId = getIntent().getIntExtra(COMPAIN_ID, 0);
        initPage();
    }

    private void initView() {
        initToolbar();
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.materialRefreshLayout_ware_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_ware_list);
        textSummary = (TextView) findViewById(R.id.text_summary);

    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar) findViewById(R.id.toolbar_ware_list);

        toolbar.setTitle("商品列表");
        toolbar.setRightButtonIcon(R.drawable.icon_grid_32);
        toolbar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               convertView(v);
            }
        });
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFULT);
        tabLayout.addTab(tab);

        TabLayout.Tab tabPrice = tabLayout.newTab();
        tabPrice.setText("价格");
        tabPrice.setTag(TAG_PRICE);
        tabLayout.addTab(tabPrice, 1);

        TabLayout.Tab tabSaleCount = tabLayout.newTab();
        tabSaleCount.setText("销量");
        tabSaleCount.setTag(TAG_SALE_COUNT);
        tabLayout.addTab(tabSaleCount, 2);
        tabLayout.addOnTabSelectedListener(this);
    }

    private void initPage() {

        mPage = Page.newBuilder()
                .setUrl(Constants.CAMPAIGN_WARE_LIST)
                .setParams("campaignId", mCampaignId)
                .setParams("orderBy", 0)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(new Page.OnPageListener() {
                    @Override
                    public void load(List list, int totalPage, int totalCount) {
                        textSummary.setText("共有" + totalCount + "件商品");
                        if (mAdapter == null)
                            initAdapterAndSetRecyclerViewAfterLoad(list);
                        else
                            loadDataToRecyclerView(list);
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
                        Log.e(TAG, "loadMore: ");
                        mData.addAll(list);
                        mAdapter.notifyItemRangeChanged(position, mData.size());
                        mRecyclerView.scrollToPosition(position);
                    }
                }).build(this, new TypeToken<WaresPage<Ware>>() {
                }.getType());

        mPage.request();
    }

    private void loadDataToRecyclerView(List list) {
        int size = list.size();
        mData.clear();
        for (int i = 0; i < size; i++) {
            mData.add((Ware) list.get(i));
            mAdapter.notifyItemChanged(i);
        }
        mRecyclerView.scrollToPosition(0);

    }

    private void initAdapterAndSetRecyclerViewAfterLoad(List list) {
        mData = list;
        mAdapter = new HotWareAdapter(this, mData);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                T.showTips("点击了位置:");
                WareDetailActivity.startThisActivity(WareListActivity.this
                        ,mData.get(position));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerDecoration());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public static void startThisActivity(Context source, Campaigns.Campaign campaign) {
        Intent intent = new Intent(source, WareListActivity.class);
        intent.putExtra(COMPAIN_ID, campaign.getId());
        source.startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int orderBy = (int) tab.getTag();
        mPage.setParams("orderBy", orderBy);
        mPage.request();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void convertView(View view) {

        ImageButton btn= (ImageButton) view;
        if (btn.getTag() == null || (int) btn.getTag() == TAG_GRIDVIEW) {
            mAdapter.resetLayoutItemId(R.layout.category_wares_grid_item);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            btn.setImageResource(R.drawable.icon_list_32);
            btn.setTag(TAG_LISTVIEW);
        } else if ((int) btn.getTag() == TAG_LISTVIEW) {
            mAdapter.resetLayoutItemId(R.layout.hot_ware_item);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            btn.setImageResource(R.drawable.icon_grid_32);
            btn.setTag(TAG_GRIDVIEW);
        }
        //这句代码很关键，不设置则不会全部更新，会造成画面错乱。
        mRecyclerView.setAdapter(mAdapter);
    }
}
