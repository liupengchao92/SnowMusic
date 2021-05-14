package com.lpc.snowmusic.http.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Author: liupengchao
 * Date: 2021/4/1
 * ClassName :BaiduApiService
 * Desc:百度ApiService
 * */
interface BaiduApiService {

    companion object {

        const val V1_TING = "v1/restserver/ting"

        const val SEARCH_CATALOGSUG = "baidu.ting.search.catalogSug"
        const val SEARCH_SUGGESTION = "baidu.ting.search.suggestion"
        const val SONG_LRC = "baidu.ting.song.lry "
        const val SONG_PLAY = "baidu.ting.song.play"
        const val GET_SONGINFO = "baidu.ting.song.getInfos"
        const val GET_ARTISTINFO = "baidu.ting.artist.getinfo"    //获取歌手信息
        const val GET_ARTISTSONGLIST = "baidu.ting.artist.getSongList" //获取歌手的歌曲列表
        const val GET_ARTISTALUBMLIST = "baidu.ting.artist.getAlbumList"   //获取歌手的专辑列表;
        const val GET_ALBUMINFO = "baidu.ting.album.getAlbumInfo"
        const val QUERY_MERGE = "baidu.ting.search.merge"
        const val GET_PLAY_MV = "baidu.ting.mv.playMV"

        const val GET_CATEGORY_LIST = "baidu.ting.radio.getCategoryList"
        const val GET_CHANNEL_SONG = "baidu.ting.radio.getChannelSong"

        const val GET_BILLCATEGORY = "baidu.ting.billboard.billCategory" //榜单
        const val GET_BILL_LIST = "baidu.ting.billboard.billList"

        const val PAGESIZE = 20

    }


    fun getDownloadUrlBySongId(songId: String): String {
        return "http://ting.baidu.com/data/music/links?songIds=$songId"
    }

    /**
     * 获取歌词
     */
    @GET
    fun getBaiduLyric(@Url baseUrl: String): Observable<ResponseBody>


}