package com.lpc.snowmusic.http.api

import com.lpc.snowmusic.bean.ArtistsInfo
import com.lpc.snowmusic.bean.BannerResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: liupengchao
 * Date: 2021/4/1
 * ClassName :NeteaseApiService
 * Desc:网易云接口ApiService
 * */
interface NeteaseApiService {
    /**
     * 发现轮播图
     * */
    @GET("banner")
    fun getBanner(): Observable<BannerResult>

    /**
     * 发现热门歌手
     * */
    @GET("/toplist/artist")
    fun getTopArtists(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArtistsInfo>
}