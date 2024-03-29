package com.lpc.snowmusic.constant

/**
 * Author: liupengchao
 * Date: 2020/5/27
 * ClassName :HttpConstant
 * Desc:
 */
object HttpConstant {
    //网易云音乐接口
    const val BASE_NETEASE_URL = "http://musiclake.leanapp.cn"
    //百度
    const val BASE_URL_BAIDU_MUSIC = "http://musicapi.qianqian.com/"
    //缓存文件夹名称
    const val HTTP_CACHE = "HttpCache"
    //缓存大小
    const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50
    //连接时间
    const val CONNECT_TIMEOUT: Long = 30L
    //读取时间
    const val READ_TIMEOUT: Long = 30L
    //写入时间
    const val WRITE_TIMEOUT: Long = 30L
}