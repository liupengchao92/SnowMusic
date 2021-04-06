package com.lpc.snowmusic.utils

import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :MusicUtils
 * Desc:
 */
object MusicUtils {
    val PIC_SIZE_SMALL = 0
    val PIC_SIZE_NORMAL = 1
    val PIC_SIZE_BIG = 2
    /**
     * 根据不同的歌曲类型生成不同的图片
     * @param size
     */
    /**
     * 根据不同的歌曲类型生成不同的图片
     * @param size
     */
    fun getAlbumPic(url: String?, type: String?, size: Int): String? {
        println(url)
        return when (type) {
            Constants.QQ -> {
                when (size) {
                    PIC_SIZE_SMALL -> {
                        url?.replace("150x150", "90x90")
                    }
                    PIC_SIZE_NORMAL -> {
                        url?.replace("150x150", "150x150")
                    }
                    else -> {
                        url?.replace("150x150", "300x300")
                    }
                }
            }
            Constants.XIAMI -> {
                val tmp = url?.split("@")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$tmp@1e_1c_100Q_90w_90h"
                    PIC_SIZE_NORMAL -> "$tmp@1e_1c_100Q_150w_150h"
                    else -> "$tmp@1e_1c_100Q_450w_450h"
                }
            }
            Constants.NETEASE -> {
                val temp = url?.split("?")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$temp?param=90y90"
                    PIC_SIZE_NORMAL -> "$temp?param=150y150"
                    else -> "$temp?param=450y450"
                }
            }
            Constants.BAIDU -> {
                val tmp = url?.split("@")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$tmp@s_1,w_90,h_90"
                    PIC_SIZE_NORMAL -> "$tmp@s_1,w_150,h_150"
                    else -> "$tmp@s_1,w_450,h_450"
                }
            }
            else -> {
                url
            }
        }
    }
}