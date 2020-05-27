package com.lpc.snowmusic.http.interceptor

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: liupengchao
 * Date: 2020/5/27
 * ClassName :HeadInterceptor
 * Desc:请求头
 */
class HeadInterceptor : Interceptor {

    companion object {
        const val TAG = "HeadInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        //请求格式
        builder.addHeader("Content-type", "application/json; charset=utf-8")
        //域名
        val domain = request.url.host
        //url
        val url = request.url.toString()
        LogUtils.d(TAG, url)
        return chain.proceed(request)
    }
}