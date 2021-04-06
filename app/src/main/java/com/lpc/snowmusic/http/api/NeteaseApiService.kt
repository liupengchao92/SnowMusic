package com.lpc.snowmusic.http.api

import com.lpc.snowmusic.http.bean.BannerBean
import com.lpc.snowmusic.http.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Author: liupengchao
 * Date: 2021/4/1
 * ClassName :NeteaseApiService
 * Desc:网易云接口ApiService
 * */
interface NeteaseApiService {

    @GET("banner")
    fun getBanner(): Observable<HttpResult<MutableList<BannerBean>>>
}