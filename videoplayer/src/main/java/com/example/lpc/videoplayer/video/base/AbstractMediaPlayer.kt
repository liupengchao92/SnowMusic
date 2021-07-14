package com.example.lpc.videoplayer.video.base

import android.media.TimedText
import com.example.lpc.videoplayer.video.base.IMediaPlayer.Companion.STATE_IDLE

/**
 * Author: liupengchao
 * Date: 2021/7/9
 * ClassName :AbstractMediaPlayer
 * Desc:
 */
abstract class AbstractMediaPlayer : IMediaPlayer {

    var currentState: Int = STATE_IDLE

    var onPreparedListener: IMediaPlayer.OnPreparedListener? = null

    var onBufferingUpdateListener: IMediaPlayer.OnBufferingUpdateListener? = null

    var onVideoSizeChangedListener: IMediaPlayer.OnVideoSizeChangedListener? = null

    var onSeekCompleteListener: IMediaPlayer.OnSeekCompleteListener? = null

    var onCompletionListener: IMediaPlayer.OnCompletionListener? = null

    var onInfoListener: IMediaPlayer.OnInfoListener? = null

    var onErrorListener: IMediaPlayer.OnErrorListener? = null

    var onTimedTextListener: IMediaPlayer.OnTimedTextListener? = null


    override fun getPlayState(): Int = currentState

    open fun updateStatus(state: Int) {
        currentState = state
    }

    open fun resetListener() {

        onPreparedListener = null

        onBufferingUpdateListener = null

        onVideoSizeChangedListener = null

        onSeekCompleteListener = null

        onCompletionListener = null

        onInfoListener = null

        onErrorListener = null

        onTimedTextListener = null
    }


    protected fun notifyOnPrepared() {
        onPreparedListener?.onPrepared(this)
    }

    protected fun notifyOnBufferingUpdate(percent: Int) {
        onBufferingUpdateListener?.onBufferUpdate(this, percent)
    }

    protected fun notifyOnVideoSizeChanged(var1: Int, var2: Int, var3: Int, var4: Int) {
        onVideoSizeChangedListener?.onVideoSizeChange(this, var1, var2, var3, var4)
    }

    protected fun notifyOnSeekCompletion() {
        onSeekCompleteListener?.onSeekComplete(this)
    }

    protected fun notifyOnCompletion() {
        onCompletionListener?.onCompletion(this)
    }

    protected fun notifyOnInfo(var1: Int, var2: Int): Boolean? {
        return onInfoListener?.onInfo(this, var1, var2)
    }

    protected fun notifyOnError(var1: Int, var2: Int): Boolean? {
        return onErrorListener?.onError(this, var1, var2)
    }

    protected fun notifyOnTimeText(text: TimedText?) {
        onTimedTextListener?.onTimedText(this, text)
    }

}