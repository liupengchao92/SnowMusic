package com.lpc.snowmusic.bean

import com.google.gson.annotations.SerializedName
import com.lpc.snowmusic.http.bean.BaseBean

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :SearchInfo
 * Desc:
 */
data class SearchInfo(
    @SerializedName("result")
    val result: Result,
    @SerializedName("code")
    val code: Int = 0
) : BaseBean()

data class HotsItem(
    @SerializedName("first")
    val first: String = "",
    @SerializedName("second")
    val second: Int = 0
)

data class Result(
    @SerializedName("hots")
    val hots: MutableList<HotsItem>?,
    @SerializedName("playlists")
    val playlists: MutableList<PlaylistsItem>?,
    @SerializedName("playlistCount")
    val playlistCount: Int,
    @SerializedName("mvs")
    val mvs: MutableList<MvInfoDetail>?,
    @SerializedName("mvCount")
    val mvCount: Int,
    @SerializedName("artists")
    val artists: MutableList<ArtistInfo>?,
    @SerializedName("artistCount")
    val artistCount: Int
)