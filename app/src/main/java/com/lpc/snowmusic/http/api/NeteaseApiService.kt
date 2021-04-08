package com.lpc.snowmusic.http.api

import com.lpc.snowmusic.bean.ArtistsInfo
import com.lpc.snowmusic.bean.BannerResult
import com.lpc.snowmusic.bean.MvInfo
import com.lpc.snowmusic.bean.PersonalizedInfo
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
     * 发现--轮播图
     * */
    @GET("banner")
    fun getBanner(): Observable<BannerResult>

    /**
     * 发现--热门歌手
     * */
    @GET("/toplist/artist")
    fun getTopArtists(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<ArtistsInfo>

    /**
     * 发现--推荐歌单
     */
    @GET("/personalized")
    fun personalizedRecommend(): Observable<PersonalizedInfo>

    /**
     * 获取推荐Mv
     */
    @GET("/personalized/mv")
    fun personalizedMv(): Observable<PersonalizedInfo>

    /**
     * 获取Mv排行榜
     */
    @GET("/top/mv")
    fun getTopMv(@Query("offset") offset: Int, @Query("limit") limit: Int): Observable<MvInfo>

    /**
     * 获取最新Mv
     */
    @GET("/mv/first")
    fun getRecentlyMv(@Query("limit") limit: Int): Observable<MvInfo>
}