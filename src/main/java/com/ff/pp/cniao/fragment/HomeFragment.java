package com.ff.pp.cniao.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ff.pp.cniao.WareListActivity;
import com.ff.pp.cniao.bean.HomeCategory;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.adapter.HomeAdapter;
import com.ff.pp.cniao.bean.BannerBean;
import com.ff.pp.cniao.bean.Campaigns;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.cniao.view.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PP on 2017/3/20.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    private SliderLayout mSliderLayout;
    private BaseSliderView.OnSliderClickListener mSlideListener;
    private RecyclerView mRecyclerView;
    private List<Campaigns> mNetData;
    private HomeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initSliderView(view);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_home);
        mNetData = new ArrayList<>();
        addDataToListFromNet();
        mAdapter = new HomeAdapter(getContext(), mNetData);

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new DividerDecoration());
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Campaigns.Campaign campaign) {

                startAnimator(view,campaign);

            }
        });
    }

    private void startAnimator(View view, final Campaigns.Campaign campaign) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"rotationX",0f,360f)
                .setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                WareListActivity.startThisActivity(getContext(),campaign);
            }
        });
        animator.start();
    }

    private void addDataToListFromNet() {
        OkHttpHelper.getInstance().get(Constants.CAMPAIGN_URL,
                new SpotsDialogCallBack<List<Campaigns>>(getContext()) {
                    @Override
                    public void onSuccess(Response response, List<Campaigns> list) {
                        mNetData.clear();
                        mNetData.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    private void initSliderView(View view) {
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_layout);

        mSlideListener = new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                switch (slider.getDescription()) {
                    //TODO
                }
            }
        };

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(1500);

        requestSliderViewImage();

    }

    private void requestSliderViewImage() {
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
        if (mSliderLayout != null) {
            mSliderLayout.removeAllSliders();
        } else {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            addSliderToLayout(list.get(i).getName(), list.get(i).getImgUrl(), mSliderLayout);
        }
    }

    private void addSliderToLayout(String title, String path, SliderLayout sliderLayout) {
        TextSliderView textSliderView = new TextSliderView(getContext());
        textSliderView.description(title)
                .image(path)
                .setOnSliderClickListener(mSlideListener);

        sliderLayout.addSlider(textSliderView);
    }

    @Override
    public void onStop() {
        mSliderLayout.startAutoCycle();
        super.onStop();
    }
}
