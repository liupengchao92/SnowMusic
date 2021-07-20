package com.example.lpc.videoplayer.video.video

import android.view.Surface
import com.example.lpc.videoplayer.video.config.PlayerConfigManager
import com.example.lpc.videoplayer.video.listener.MediaPlayerListener
import com.example.lpc.videoplayer.video.player.base.AbstractMediaPlayer
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer

/**
 * Author: liupengchao
 * Date: 2021/7/20
 * ClassName :VideoManager
 * Desc:
 */
class VideoManager : IMediaPlayer.OnPreparedListener, IMediaPlayer.OnCompletionListener,
    IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnInfoListener,
    IMediaPlayer.OnErrorListener, IMediaPlayer.OnVideoSizeChangedListener, VideoViewBridge {

    private var player: AbstractMediaPlayer? = null
    private var mediaPlayerListener: MediaPlayerListener? = null

    init {
        player = PlayerConfigManager.getPlayer()?.apply {
            onPreparedListener = this@VideoManager
            onCompletionListener = this@VideoManager
            onErrorListener = this@VideoManager
            onBufferingUpdateListener = this@VideoManager
            onInfoListener = this@VideoManager
            onVideoSizeChangedListener = this@VideoManager
        }
    }

    override fun start() {
        player?.start()
    }

    override fun stop() {
        player?.stop()
    }

    override fun pause() {
        player?.pause()
    }

    override fun getVideoWidth(): Int = player?.getVideoWidth() ?: 0

    override fun getVideoHeight(): Int = player?.getVideoHeight() ?: 0

    override fun isPlaying(): Boolean = player?.isPlaying() ?: false

    override fun seekTo(var1: Long) {
        player?.seekTo(var1.toInt())
    }

    override fun getCurrentPosition(): Long = player?.getCurrentPosition() ?: 0

    override fun getDuration(): Long = player?.getDuration() ?: 0

    override fun setDisplay(var1: Surface?) {
        player?.setSurface(var1)
    }

    override fun releaseSurface(var1: Surface?) {
        player?.release()
    }

    override fun setMediaPlayerListener(mediaPlayerListener: MediaPlayerListener) {
        this.mediaPlayerListener = mediaPlayerListener
    }

    override fun getMediaPlayerListener(): MediaPlayerListener? = mediaPlayerListener

    override fun onPrepared(mediaPlayer: IMediaPlayer?) {
        mediaPlayerListener?.onPrepared()
    }

    override fun onCompletion(mediaPlayer: IMediaPlayer?) {
        mediaPlayerListener?.onCompletion()
    }

    override fun onInfo(mediaPlayer: IMediaPlayer?, var1: Int, var2: Int): Boolean {
        mediaPlayerListener?.onInfo(var1, var2)
        return true
    }

    override fun onError(mediaPlayer: IMediaPlayer?, var1: Int, var2: Int): Boolean {
        mediaPlayerListener?.onError(var1, var2)
        return true
    }

    override fun onBufferUpdate(mediaPlayer: IMediaPlayer?, var1: Int) {
        mediaPlayerListener?.onBufferingUpdate(var1)
    }

    override fun onVideoSizeChange(
        mediaPlayer: IMediaPlayer?,
        var1: Int,
        var2: Int,
        var3: Int,
        var4: Int
    ) {
        mediaPlayerListener?.onVideoSizeChanged()
    }


}