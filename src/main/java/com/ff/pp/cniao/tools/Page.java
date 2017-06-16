package com.ff.pp.cniao.tools;

import android.content.Context;
import android.graphics.Color;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.ff.pp.cniao.bean.WaresPage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


/**
 * Created by PP on 2017/4/1.
 */

public class Page {

    private static final int REFRESH_DATA = 1;
    private static final int INITIAL_LOAD = 2;
    private static final int LOAD_MORE = 3;

    private static Builder builder;
    private int mState = INITIAL_LOAD;

    private Page() {
        initAndSetRefreshLayout();
    }

    public static Builder newBuilder() {
        builder = new Builder();
        return builder;
    }

    private void initAndSetRefreshLayout() {

        builder.mRefreshLayout.setLoadMore(builder.canLoadMore);
        builder.mRefreshLayout.setProgressColors(new int[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED});
        builder.mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mState = REFRESH_DATA;
                refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mState = LOAD_MORE;
                loadMoreData();

            }
        });
    }

    public void request() {
        requestData();
    }

    public void setParams(String key, Object value) {
        builder.setParams(key, value);
    }

    private void refreshData() {

        builder.mCurPage=1;
        requestData();

    }

    private void loadMoreData() {
        if (builder.mCurPage <= builder.mTotalPage) {
            builder.mCurPage++;
            requestData();
        } else {
            T.showTips( "无更多数据");
            builder.mRefreshLayout.finishRefreshLoadMore();
            mState=INITIAL_LOAD;
        }
    }


    private void requestData() {
        OkHttpHelper.getInstance().get(buildUrl(), new RequestCallBack(builder.mContext));
    }

    private <T> void sendData(List<T> list, int totalPage, int totalCount) {

        switch (mState) {
            case INITIAL_LOAD:
                if (builder.listener != null) {
                    builder.listener.load(list, totalPage, totalCount);
                }
                break;
            case REFRESH_DATA:
                if (builder.listener != null) {
                    builder.listener.refresh(list, totalPage, totalCount);
                }
                mState=INITIAL_LOAD;
                builder.mRefreshLayout.finishRefresh();
                break;
            case LOAD_MORE:
                if (builder.listener != null) {
                    builder.listener.loadMore(list, totalPage, totalCount);
                }
                mState=INITIAL_LOAD;
                builder.mRefreshLayout.finishRefreshLoadMore();
                break;
        }

    }

    private String buildUrl() {
        return builder.url + "?" + buildUrlParams();
    }

    private String buildUrlParams() {

        HashMap<String, Object> map = (HashMap<String, Object>) builder.mParams;
        map.put("curPage", builder.mCurPage);
        map.put("pageSize", builder.mPageSize);
        
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == '&')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();

    }

    public static class Builder {
        private String url;
        private int mPageSize = 10;
        private int mCurPage = 1;
        private int mTotalPage = 4;
        private Map<String, Object> mParams;
        private boolean canLoadMore;
        private Context mContext;

        private OnPageListener listener;

        private MaterialRefreshLayout mRefreshLayout;
        public Type mType;

        public Builder() {
            mParams = new HashMap<>(5);

        }

        public Builder setOnPageListener(OnPageListener listener) {
            this.listener = listener;
            return builder;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.mRefreshLayout = refreshLayout;
            return builder;
        }

        public Builder setPageSize(int pageSize) {
            this.mPageSize = pageSize;
            return builder;
        }

        public Builder setCurPage(int curPage) {
            this.mCurPage = curPage;
            return builder;
        }

        public Builder setTotalPage(int totalPage) {
            this.mTotalPage = totalPage;
            return builder;
        }

        public Builder setParams(String key, Object value) {
            mParams.put(key, value);
            return builder;
        }

        public Builder setLoadMore(boolean loadMore) {
            this.canLoadMore = loadMore;
            return builder;
        }

        public Page build(Context context, Type callBackDataType) {

            mContext = context;
            mType = callBackDataType;
            valid();
            return new Page();
        }

        private void valid() {
            if (mContext == null)
                throw new RuntimeException("context can't be null");
            if (url == null)
                throw new RuntimeException("url can't be null");
            if (mRefreshLayout == null)
                throw new RuntimeException("materialRefreshLayout can't be null");
        }
    }

    public interface OnPageListener<T> {
        void load(List<T> list, int totalPage, int totalCount);

        void refresh(List<T> list, int totalPage, int totalCount);

        void loadMore(List<T> list, int totalPage, int totalCount);
    }

    class RequestCallBack<T> extends SpotsDialogCallBack<WaresPage<T>> {

        public RequestCallBack(Context context) {
            super(context);
            super.type = builder.mType;
        }

        @Override
        public void onSuccess(Response response, WaresPage<T> page) {
            builder.mCurPage = page.getCurrentPage();
            builder.mTotalPage = page.getTotalPage();
            sendData(page.getList(), page.getTotalPage(), page.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {

        }
    }
}
