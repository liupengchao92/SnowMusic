package com.lpc.snowmusic.constant

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :Constant
 * Desc:常量
 */
object Constants {

    const val FRAGMENT_KEY: String = "FRAGMENT_KEY"

    //歌曲类型
    const val LOCAL = "local"
    const val QQ = "qq"
    const val XIAMI = "xiami"
    const val BAIDU = "baidu"
    const val NETEASE = "netease"
    const val VIDEO = "video"//本地视频
    const val YOUTUBE = "youtube"//YouTube

    //在线音乐
    const val FILENAME_MP3 = ".mp3"
    const val FILENAME_LRC = ".lrc"

    //网易云歌单
    const val PLAYLIST_WY_ID = "playlist_wy"

    //歌单类型
    const val PLAYLIST_LOVE_ID = "love"
    const val PLAYLIST_HISTORY_ID = "history"
    const val PLAYLIST_LOCAL_ID = "local"
    const val PLAYLIST_QUEUE_ID = "queue"
    const val PLAYLIST_DOWNLOAD_ID = "download"

    //在线歌单
    const val PLAYLIST_CUSTOM_ID = "custom_online"

    const val HTTP = "http"

    const val DEFAULT_NOTIFICATION = "notification"

    /**
     * 悬浮窗权限requestCode
     */
    const val REQUEST_CODE_FLOAT_WINDOW = 0x123
    const val REQUEST_CODE_LOGIN = 10001


}