package com.ff.pp.cniao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.ff.pp.cniao.WareDetailActivity;
import com.ff.pp.cniao.adapter.BaseAdapter;
import com.ff.pp.cniao.adapter.CategoryListAdapter;
import com.ff.pp.cniao.adapter.CategoryWareAdapter;
import com.ff.pp.cniao.bean.BannerBean;
import com.ff.pp.cniao.bean.CategoryListBean;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WaresPage;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.view.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by PP on 2017/3/20.
 */

public class CategoryFragment extends BaseFragment {
    private static final String TAG = "CategoryFragment";
    private RecyclerView mRecyclerViewList, mRecyclerViewMain;
    private SliderLayout mSliderLayout;
    private CategoryListAdapter mAdapter;
    private List<CategoryListBean> mCategories;

    private CategoryWareAdapter mCategoryWareAdapter;
    private List<Ware> mWares;
    private int mCurrentCategoryId;

    private static final int ININTIAL_LOAD=0;
    private static final int STATE_DOWN_FRESH =1;
    private static final int STATE_LOAD_MORE =2;
    private int mCurrentStatus;

    private MaterialRefreshLayout mRefreshLayout;
    private int mCurrentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);

        initCategoryList(view);
        initSliderView(view);
        initCategoryWares(view);
        initAndSetRefreshLayout(view);
        return view;
    }

    private void initCategoryList(View view) {
        mRecyclerViewList = (RecyclerView) view.findViewById(R.id.recyclerView_category_list);
        mCategories = new ArrayList<>();

        requestCategoryListDate();
        mAdapter = new CategoryListAdapter(getContext(), mCategories);
        mRecyclerViewList.setAdapter(mAdapter);
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewList.addItemDecoration(new DividerDecoration());

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCurrentCategoryId=mCategories.get(position).getSort();
                resetCategoryStatus();
                requestCategoryWaresDate(mCurrentCategoryId);
            }
        });
    }

    private void requestCategoryListDate() {
        OkHttpHelper.getInstance().get(Constants.CATEGORY_LIST,
                new SpotsDialogCallBack<List<CategoryListBean>>(getContext()) {
                    @Override
                    public void onSuccess(Response response, List<CategoryListBean> list) {
                        mCategories.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void initSliderView(View view) {
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_layout_category);

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        mSliderLayout.setDuration(2500);
        requestImage();

    }

    private void requestImage() {
        String url = Constants.BANNER_URL + "?type=1";

        OkHttpHelper.getInstance().get(url, new SpotsDialogCallBack<List<BannerBean>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<BannerBean> list) {
                refreshSlider(list);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void refreshSlider(List<BannerBean> list) {
        if (mSliderLayout == null) {
            return;
        }
        mSliderLayout.removeAllSliders();
        for (int i = 0; i < list.size(); i++) {
            addSliderToLayout(list.get(i).getImgUrl(), mSliderLayout);
        }
    }

    private void addSliderToLayout(String path, SliderLayout sliderLayout) {
        DefaultSliderView sliderView = new DefaultSliderView(getContext());
        sliderView.image(path);
        sliderLayout.addSlider(sliderView);
    }

    private void initCategoryWares(View view) {
        mRecyclerViewMain = (RecyclerView) view.findViewById(R.id.recyclerView_category_main);
        mWares = new ArrayList<>();

        mCategoryWareAdapter = new CategoryWareAdapter(getContext(), mWares);
        mRecyclerViewMain.setAdapter(mCategoryWareAdapter);
        mRecyclerViewMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerViewMain.addItemDecoration(new DividerDecoration());
        mRecyclerViewMain.setNestedScrollingEnabled(false);
        mCurrentCategoryId=1;
        resetCategoryStatus();
        requestCategoryWaresDate(mCurrentCategoryId);
        mCategoryWareAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WareDetailActivity.startThisActivity(getContext(), mWares.get(position));
            }
        });
    }

    private void resetCategoryStatus() {
        mCurrentStatus=ININTIAL_LOAD;
        mCurrentPage=1;
    }

    private void requestCategoryWaresDate(int categoryId) {
        String url = Constants.CATEGORY_WARE_URL + "?curPage="+mCurrentPage+"&pageSize=10&categoryId=" + categoryId;
        OkHttpHelper.getInstance().get(url,
                new SpotsDialogCallBack<WaresPage<Ware>>(getContext()) {
                    @Override
                    public void onSuccess(Response response, WaresPage waresPage) {
                        showCategoryWare(waresPage);
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private void showCategoryWare(WaresPage waresPage) {
        switch (mCurrentStatus){
            case ININTIAL_LOAD:

                mWares.clear();
                mWares.addAll(waresPage.getList());
                mCategoryWareAdapter.notifyDataSetChanged();
                break;
            case STATE_DOWN_FRESH:
                mWares.clear();
                mWares.addAll(waresPage.getList());
                mCategoryWareAdapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();

            case STATE_LOAD_MORE:
                int position=mWares.size();
                mWares.addAll(waresPage.getList());
                mCategoryWareAdapter.notifyItemRangeChanged(position,mWares.size());
                mRecyclerViewMain.scrollToPosition(position);
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }

    }

    private void initAndSetRefreshLayout(View view) {
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_layout_category);
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setProgressColors(new int[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED});
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                resetCategoryStatus();
                mCurrentStatus= STATE_DOWN_FRESH;
                requestCategoryWaresDate(mCurrentCategoryId);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mCurrentStatus= STATE_LOAD_MORE;
                mCurrentPage++;
                requestCategoryWaresDate(mCurrentCategoryId);
            }
        });
    }
}


