package com.example.lpc.videoplayer.video.video

import android.view.Surface
import com.example.lpc.videoplayer.video.entity.DataSource
import com.example.lpc.videoplayer.video.listener.MediaPlayerListener

/**
 * Author: liupengchao
 * Date: 2021/7/20
 * ClassName :VideoViewBridge
 * Desc:
 */
interface VideoViewBridge {

    fun setDataSource(dataSource: DataSource)

    fun start()

    fun stop()

    fun pause()

    fun release()

    fun getVideoWidth(): Int

    fun getVideoHeight(): Int

    fun isPlaying(): Boolean

    fun seekTo(var1: Long)

    fun getCurrentPosition(): Long

    fun getDuration(): Long

    fun setDisplay(var1: Surface?)

    fun releaseSurface(var1: Surface?)

    fun setMediaPlayerListener(mediaPlayerListener: MediaPlayerListener)

    fun getMediaPlayerListener(): MediaPlayerListener?

    fun getPlayState(): Int?


}