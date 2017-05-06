package com.simonc312.androidapiexercise.api.interceptors;

import android.os.Build;

import com.simonc312.androidapiexercise.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        Request requestWithHeader = request.newBuilder()
                .addHeader("User-Agent", String.format("AndroidApiExercise/%s/Platform/Android OS/%s",
                        BuildConfig.VERSION_NAME, Build.VERSION.SDK_INT))
                .build();
        return chain.proceed(requestWithHeader);
    }
}
