package com.ff.pp.cniao;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ff.pp.cniao.fragment.HomeFragment;
import com.ff.pp.cniao.fragment.MineFragment;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.bean.Tab;
import com.ff.pp.cniao.fragment.CartFragment;
import com.ff.pp.cniao.fragment.HotWaresFragment;
import com.ff.pp.cniao.fragment.CategoryFragment;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();
    }

    private void initTab() {
        mTabs = new ArrayList<>(5);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),R.id.realtabcontent);

        mTabs.add(new Tab(R.string.home, R.drawable.home_icon_selector,
                HomeFragment.class));
        mTabs.add(new Tab(R.string.find,R.drawable.discover_icon_selector ,
                HotWaresFragment.class));
        mTabs.add(new Tab(R.string.category,R.drawable.hot_ware_icon_selector ,
                CategoryFragment.class));
        mTabs.add(new Tab(R.string.cart, R.drawable.cart_icon_selector,
                CartFragment.class));
        mTabs.add(new Tab(R.string.mine, R.drawable.avatar_icon_selector,
                MineFragment.class));

        for (Tab tab : mTabs) {
            mTabHost.addTab(mTabHost.newTabSpec(getString(tab.getTextId()))
                    .setIndicator(buildIndicator(tab)), tab.getFragment(), null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
//        mTabHost.setBackgroundColor(getResources().getColor(R.color.tab_background));
    }

    @NonNull
    private View buildIndicator(Tab tab) {
        mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.tab_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_icon);
        TextView textView = (TextView) view.findViewById(R.id.textView_icon);

        imageView.setImageResource(tab.getImageId());
        textView.setText(tab.getTextId());
        return view;
    }
}
