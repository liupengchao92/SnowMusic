package com.lpc.snowmusic.bean

import com.google.gson.annotations.SerializedName
import com.lpc.snowmusic.http.bean.BaseBean

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :ArtistsInfo
 * Desc:
 */
data class ArtistsInfo(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("list")
    val list: List
) : BaseBean()


data class ArtistInfo(
    @SerializedName("lastRank")
    val lastRank: Int = 0,
    @SerializedName("img1v1Url")
    val imgVUrl: String = "",
    @SerializedName("musicSize")
    val musicSize: Int = 0,
    @SerializedName("img1v1Id")
    val imgVId: Long = 0,
    @SerializedName("albumSize")
    val albumSize: Int = 0,
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("score")
    val score: Int = 0,
    @SerializedName("topicPerson")
    val topicPerson: Int = 0,
    @SerializedName("briefDesc")
    val briefDesc: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("picId")
    val picId: Long = 0,
    @SerializedName("trans")
    val trans: String = ""
)


data class List(
    @SerializedName("artists")
    val artists: MutableList<ArtistInfo>?,
    @SerializedName("updateTime")
    val updateTime: Long = 0,
    @SerializedName("type")
    val type: Int = 0
)