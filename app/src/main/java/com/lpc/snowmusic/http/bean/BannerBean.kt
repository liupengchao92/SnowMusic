package com.lpc.snowmusic.http.bean

import com.google.gson.annotations.SerializedName

/**
 * Author: liupengchao
 * Date: 2020/9/27
 * ClassName :BannerBean
 * Desc:
 */
data class BannerBean(
    @SerializedName("imageUrl")
    val picUrl: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("targetId")
    val targetId: String,
    @SerializedName("backgroundUrl")
    val backgroundUrl: String,
    @SerializedName("targetType")
    val targetType: String,
    @SerializedName("typeTitle")
    val typeTitle: String,
    @SerializedName("monitorType")
    val monitorType: String,
    @SerializedName("monitorImpress")
    val monitorImpress: String,
    @SerializedName("monitorClick")
    val monitorClick: String
)