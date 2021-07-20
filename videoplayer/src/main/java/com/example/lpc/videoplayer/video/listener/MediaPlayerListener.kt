package com.example.lpc.videoplayer.video.listener

/**
 * Author: liupengchao
 * Date: 2021/7/20
 * ClassName :MediaPlayerListener
 * Desc:播放相关监听
 */
interface MediaPlayerListener {

    fun onPrepared()

    fun onCompletion()

    fun onBufferingUpdate(var1: Int)

    fun onSeekComplete()

    fun onError(var1: Int, var2: Int)

    fun onInfo(var1: Int, var2: Int)

    fun onVideoSizeChanged()

}