package com.lpc.snowmusic.bean

import com.cyl.musicapi.netease.Creator
import com.cyl.musicapi.netease.TrackId
import com.cyl.musicapi.netease.TracksItem
import com.google.gson.annotations.SerializedName
import com.lpc.snowmusic.http.bean.BaseBean

/**
 * Author: liupengchao
 * Date: 2021/4/19
 * ClassName :NeteasePlaylistDetail
 * Desc:歌单详情
 */
data class PlaylistDetail(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("playlist")
    val playlist: PlayListItem?
) : BaseBean()

data class PlayListItem(
    @SerializedName("adType")
    val adType: Int,
    @SerializedName("cloudTrackCount")
    val cloudTrackCount: Int,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("commentThreadId")
    val commentThreadId: String,
    @SerializedName("coverImgId")
    val coverImgId: Long,
    @SerializedName("coverImgId_str")
    val coverImgIdStr: String,
    @SerializedName("coverImgUrl")
    val coverImgUrl: String,
    @SerializedName("createTime")
    val createTime: Long,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("description")
    val description: String,
    @SerializedName("highQuality")
    val highQuality: Boolean,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("newImported")
    val newImported: Boolean,
    @SerializedName("ordered")
    val ordered: Boolean,
    @SerializedName("playCount")
    val playCount: Int,
    @SerializedName("privacy")
    val privacy: Int,
    @SerializedName("shareCount")
    val shareCount: Int,
    @SerializedName("specialType")
    val specialType: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("subscribed")
    val subscribed: Boolean,
    @SerializedName("subscribedCount")
    val subscribedCount: Int,
    @SerializedName("subscribers")
    val subscribers: MutableList<SubscriberBean>,
    @SerializedName("tags")
    val tags: MutableList<String>,
    @SerializedName("trackCount")
    val trackCount: Int,
    @SerializedName("trackIds")
    val trackIds: MutableList<TrackId>,
    @SerializedName("trackNumberUpdateTime")
    val trackNumberUpdateTime: Long,
    @SerializedName("trackUpdateTime")
    val trackUpdateTime: Long,
    @SerializedName("tracks")
    val tracks: MutableList<TracksItem>?,
    @SerializedName("updateTime")
    val updateTime: Long,
    @SerializedName("updateFrequency")
    val updateFrequency: String?,
    @SerializedName("userId")
    val userId: Int
)

data class SubscriberBean(
    @SerializedName("accountStatus")
    val accountStatus: Int,
    @SerializedName("authStatus")
    val authStatus: Int,
    @SerializedName("authority")
    val authority: Int,
    @SerializedName("avatarImgId")
    val avatarImgId: Long,
    @SerializedName("avatarImgIdStr")
    val avatarImgIdStr: String,
    @SerializedName("avatarImgId_str")
    val avatarImgIdStr1: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("backgroundImgId")
    val backgroundImgId: Long,
    @SerializedName("backgroundImgIdStr")
    val backgroundImgIdStr: String,
    @SerializedName("backgroundUrl")
    val backgroundUrl: String,
    @SerializedName("birthday")
    val birthday: Long,
    @SerializedName("city")
    val city: Int,
    @SerializedName("defaultAvatar")
    val defaultAvatar: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("detailDescription")
    val detailDescription: String,
    @SerializedName("djStatus")
    val djStatus: Int,
    @SerializedName("expertTags")
    val expertTags: Any,
    @SerializedName("experts")
    val experts: Any,
    @SerializedName("followed")
    val followed: Boolean,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("mutual")
    val mutual: Boolean,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("province")
    val province: Int,
    @SerializedName("remarkName")
    val remarkName: Any,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("userType")
    val userType: Int,
    @SerializedName("vipType")
    val vipType: Int
)

