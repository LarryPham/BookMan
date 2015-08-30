package com.techiedb.app.bookman.services.tasks;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.utils.LogUtils;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

public class RestApiUtils {
    public static final String TAG = LogUtils.makeLogTag(RestApiUtils.class.getSimpleName());
    public static RestAdapter.Builder sBuilder;
    public static RestAdapter sRestAdapter;

    private RestApiUtils() {

    }

    public static AppRestApi getAppRestApi() {
        LogUtils.LOGD(TAG, "[BUILD] creating new instance of AppRestApi without interceptor, converter");
        sBuilder = new RestAdapter.Builder();
        sBuilder.setEndpoint(Properties.BASE_SERVER_URL);
        sBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        sRestAdapter = sBuilder.build();
        return sRestAdapter.create(AppRestApi.class);
    }

    public static AppRestApi getAppRestApi(RequestInterceptor interceptor) {
        LogUtils.LOGD(TAG, "[BUILD] creating new instance of AppRestApi with a specified RequestInterceptor");
        sBuilder = new RestAdapter.Builder();
        sBuilder.setEndpoint(Properties.BASE_SERVER_URL);
        sBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        sBuilder.setRequestInterceptor(interceptor);
        sRestAdapter = sBuilder.build();
        return sRestAdapter.create(AppRestApi.class);
    }

    public static AppRestApi getAppRestApi(RequestInterceptor interceptor, Converter converter) {
        LogUtils.LOGD(TAG, "[BUILD] creating new instance of AppRestApi with a specified RequestInterceptor and specified converter");
        sBuilder = new RestAdapter.Builder();
        sBuilder.setEndpoint(Properties.BASE_SERVER_URL);
        sBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        sBuilder.setRequestInterceptor(interceptor);
        sBuilder.setConverter(converter);
        sRestAdapter = sBuilder.build();
        return sRestAdapter.create(AppRestApi.class);
    }
}
