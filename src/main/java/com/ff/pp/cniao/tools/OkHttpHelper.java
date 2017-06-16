package com.ff.pp.cniao.tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ff.pp.cniao.Application.MyApplication;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PP on 2017/3/25.
 */

public class OkHttpHelper {
    private static final String TAG = "OkHttpHelper";
    private static final int TOKEN_ERROR = 401;
    private static final int TOKEN_EXPIRE = 402;
    private static final int TOKEN_MISSED = 403;

    private static OkHttpClient client;
    private Gson gson;
    private static OkHttpHelper helper;
    private Handler handler;

    private OkHttpHelper() {
        client = new OkHttpClient();
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance() {
        if (helper == null) {
            helper = new OkHttpHelper();
        }
        return helper;
    }

    public void get(String url, BaseCallBack callBack) {
        Request request = buildRequest(url, null, TypeHttpMethod.GET);
        doRequest(request, callBack);
    }

    public void post(String url, Map<String, String> params, BaseCallBack callBack) {
        Request request = buildRequest(url, params, TypeHttpMethod.POST);

        doRequest(request, callBack);
    }

    private Request buildRequest(String url, Map<String, String> params, TypeHttpMethod method) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (method == TypeHttpMethod.GET) {
            addTokenToUrl(builder, url);
            builder.get();
        } else if (method == TypeHttpMethod.POST) {

            builder.post(buildBodyWithToken(params));
        }
        Request request = builder.build();
        Log.e(TAG, "buildRequest: "+request.toString() );
        return request;

    }

    private void addTokenToUrl(Request.Builder builder, String url) {
        char firstChar;
        if (url.contains("?")) {
            firstChar = '&';
        } else {
            firstChar = '?';
        }
        url = url + firstChar + "token=" + MyApplication.getInstance().getToken();
        builder.url(url);
    }

    private RequestBody buildBodyWithToken(Map<String, String> params) {
        if (params == null) return null;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        if (MyApplication.getInstance().getToken() != null)
            builder.add("token", MyApplication.getInstance().getToken());
        return builder.build();
    }

    public void doRequest(final Request request, final BaseCallBack callback) {

        callback.onRequestBefore(request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(response);
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    Log.e(TAG, "onResponse: " + resultStr);
                    if (callback.type == String.class) {
                        onSuccessCallBack(callback, response, resultStr);
                    } else {
                        try {
                            Object o = gson.fromJson(resultStr, callback.type);
                            onSuccessCallBack(callback, response, o);
                        } catch (JsonParseException e) {
                            onErrorCallBack(callback, response, response.code(), e);
                        }
                    }
                } else if (response.code() == TOKEN_ERROR || response.code() == TOKEN_EXPIRE
                        || response.code() == TOKEN_MISSED) {
                    onTokenErrorCallBack(callback, response, response.code());
                } else {
                    Log.e(TAG, "onResponse: "+response.message()+","+response.code() );
                    onErrorCallBack(callback, response, response.code(), null);
                }
            }
        });
    }

    private void onSuccessCallBack(final BaseCallBack callBack, final Response response, final Object o) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, o);
            }
        });
    }

    private void onErrorCallBack(final BaseCallBack callBack, final Response response,
                                 final int code, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(response, code, e);
            }
        });
    }

    private void onTokenErrorCallBack(final BaseCallBack callBack, final Response response,
                                      final int code) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyApplication.getInstance().clearUserAndToken();
                callBack.onTokenError(response, code);
            }
        });
    }

    enum TypeHttpMethod {
        GET,
        POST
    }


}
