package com.example.lpc.videoplayer.video.base

import android.media.MediaPlayer
import android.media.TimedText
import android.view.Surface
import android.view.SurfaceHolder

/**
 * Author: liupengchao
 * Date: 2021/7/9
 * ClassName :IMediaPlayer
 * Desc:
 */
interface IMediaPlayer {

    companion object {
        const val STATE_END = -2
        const val STATE_ERROR = -1
        const val STATE_IDLE = 0
        const val STATE_INITIALIZED = 1
        const val STATE_PREPARED = 2
        const val STATE_STARTED = 3
        const val STATE_PAUSED = 4
        const val STATE_STOPPED = 5
        const val STATE_PLAYBACK_COMPLETE = 6
    }

    fun setDisplay(surfaceHolder: SurfaceHolder?)

    fun setSurface(surface: Surface?)

    fun setDataSource(url: String)

    fun getDataSource(): String

    fun prepareAsync()

    fun start()

    fun pause()

    fun stop()

    fun reset()

    fun release()

    fun resume()

    fun seekTo(position: Int)

    fun isPlaying(): Boolean

    fun setVolume(var1: Float, var2: Float)

    fun setSpeed(var1: Float)

    fun getPlayState(): Int

    fun getVideoWidth(): Int

    fun getVideoHeight(): Int

    fun getCurrentPosition(): Long

    fun getDuration(): Long

    fun getAudioSectionId(): Int


    /* fun setOnPreparedListener(onPreparedListener: OnPreparedListener?)

     fun setOnCompletionListener(onCompletionListener: OnCompletionListener?)

     fun setOnBufferingUpdateListener(onBufferingUpdateListener: OnBufferingUpdateListener?)

     fun setOnSeekCompleteListener(onSeekCompleteListener: OnSeekCompleteListener?)

     fun setOnVideoSizeChangedListener(onVideoSizeChangedListener: OnVideoSizeChangedListener?)

     fun setOnErrorListener(onErrorListener: OnErrorListener?)

     fun setOnInfoListener(onInfoListener: OnInfoListener?)
 */

    interface OnPreparedListener {
        fun onPrepared(mediaPlayer: IMediaPlayer?)
    }

    interface OnCompletionListener {
        fun onCompletion(mediaPlayer: IMediaPlayer?)
    }

    interface OnInfoListener {
        fun onInfo(mediaPlayer: IMediaPlayer?, var1: Int, var2: Int): Boolean
    }

    interface OnErrorListener {
        fun onError(mediaPlayer: IMediaPlayer?, var1: Int, var2: Int): Boolean
    }


    interface OnBufferingUpdateListener {
        fun onBufferUpdate(mediaPlayer: IMediaPlayer?, var1: Int)
    }

    interface OnVideoSizeChangedListener {
        fun onVideoSizeChange(mediaPlayer: IMediaPlayer?, var1: Int, var2: Int, var3: Int, var4: Int)
    }

    interface OnSeekCompleteListener {
        fun onSeekComplete(mediaPlayer: IMediaPlayer?)
    }

    interface OnTimedTextListener {
        fun onTimedText(mediaPlayer: IMediaPlayer?, text: TimedText?)
    }

}