package com.example.lpc.videoplayer.video.entity

import java.io.File
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/7/20
 * ClassName :DataSource
 * Desc:
 */
data class DataSource(var url: String) {

    //是否循环播放
    var looping = false

    //是否缓存
    var isCache = false

    //Raw资源文件
    var rawId = -1

    //Assets视频文件
    var assetsPath: String? = null

    //缓存路径
    var mCachePath: File? = null

    //其它数据
    var extraData: HashMap<String, String>? = null

}
