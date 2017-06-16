package com.ff.pp.cniao.tools;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PP on 2017/3/25.
 */

public abstract class BaseCallBack<T> {

    public Type type;

    public static Type getSuperClassType(Class subClass) {
        Type superclass = subClass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);

    }


    public BaseCallBack() {
        type = getSuperClassType(getClass());
    }
    public abstract void onResponse(Response response);

    public abstract void onRequestBefore(Request request);

    public abstract void onSuccess(Response response, T t);

    public abstract void onFailure(Request request, IOException e);

    public abstract void onError(Response response, int code, Exception e);

    public abstract void onTokenError(Response response, int code);

}
