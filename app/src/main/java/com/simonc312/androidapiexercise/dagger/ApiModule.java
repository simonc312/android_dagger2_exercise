package com.simonc312.androidapiexercise.dagger;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simonc312.androidapiexercise.BuildConfig;
import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.interceptors.LoggingInterceptor;
import com.simonc312.androidapiexercise.api.interceptors.UserAgentHeaderInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    UserAgentHeaderInterceptor providesUserAgentHeaderInterceptor() {
        return new UserAgentHeaderInterceptor();
    }

    @Provides
    @Singleton
    LoggingInterceptor providesLoggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Provides
    @Singleton
    StethoInterceptor providesStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@NonNull final UserAgentHeaderInterceptor userAgentHeaderInterceptor,
                                     @NonNull final LoggingInterceptor loggingInterceptor,
                                     @NonNull final StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(userAgentHeaderInterceptor)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(stethoInterceptor) //always add last to visualize end results
                .build();
    }

    @Provides
    @Singleton
    HttpUrl provideEndpoint() {
        return HttpUrl.parse(BuildConfig.BASE_URL_ENDPOINT);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull final Gson gson,
                             @NonNull final OkHttpClient httpClient,
                             @NonNull final HttpUrl endpoint) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.baseUrl(endpoint);
        return builder.build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(@NonNull final Retrofit restAdapter) {
        return restAdapter.create(ApiService.class);
    }
}
