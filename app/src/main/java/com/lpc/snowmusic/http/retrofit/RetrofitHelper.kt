package com.lpc.snowmusic.http.retrofit

import com.lpc.snowmusic.BuildConfig
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.constant.HttpConstant
import com.lpc.snowmusic.http.api.ApiService
import com.lpc.snowmusic.http.interceptor.HeadInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Author: liupengchao
 * Date: 2020/5/22
 * ClassName :RetrofitHelper
 * Desc:
 */
object RetrofitHelper {

    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    /**
     *获取 Retrofit
     * */
    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(HttpConstant.BASE_URL)
                        .client(getOkHttpClient())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }

        }
        return retrofit

    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
        //日志拦截器
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        //设置缓存文件
        val cacheFile = File(MusicApplication.context.cacheDir, HttpConstant.HTTP_CACHE)
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)
        //
        //构建OkHttpClient
        builder.run {
            cache(cache)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeadInterceptor())
            connectTimeout(HttpConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
        }
        return builder.build()
    }
}