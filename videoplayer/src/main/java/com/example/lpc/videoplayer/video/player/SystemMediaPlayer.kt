package com.example.lpc.videoplayer.video.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import com.example.lpc.videoplayer.video.entity.DataSource
import com.example.lpc.videoplayer.video.player.base.AbstractMediaPlayer
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_END
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_ERROR
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_IDLE
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_INITIALIZED
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_PAUSED
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_PLAYBACK_COMPLETE
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_PLAYING
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_PREPARED
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer.Companion.STATE_STOPPED
import com.example.lpc.videoplayer.video.utils.LogUtils

/**
 * Author: liupengchao
 * Date: 2021/7/9
 * ClassName :SystemMediaPlayer
 * Desc:系统播放器
 */
class SystemMediaPlayer : AbstractMediaPlayer() {

    companion object {

        const val TAG: String = "SystemMediaPlayer"

        const val MEDIA_INFO_NETWORK_BANDWIDTH: Int = 703
    }

    //MediaPlayer
    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }

    //
    private var audioAttributes: AudioAttributes =
        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build()

    //
    private var surface: Surface? = null

    //播放地址
    private var dataSource: DataSource? = null

    //视频宽度
    private var mVideoWidth = 0

    //视频的高度
    private var mVideoHeight = 0;


    override fun setDataSource(dataSource: DataSource) {

        if (dataSource == null) throw java.lang.Exception("播放地址url is empty")

        mediaPlayer?.run {
            try {
                if (isPlaying) {
                    stop()
                    reset()
                }
                isLooping = dataSource.isLooping
                setOnPreparedListener(mOnPreparedListener)
                setOnBufferingUpdateListener(mOnBufferingUpdateListener)
                setOnVideoSizeChangedListener(mOnVideoSizeChangedListener)
                setOnCompletionListener(mOnCompletionListener)
                setOnInfoListener(mOnInfoListener)
                setOnErrorListener(mOnErrorListener)
                setOnTimedTextListener(mOnTimedTextListener)


                setDataSource(dataSource.url)
                setScreenOnWhilePlaying(true)
                setAudioAttributes(audioAttributes)
                prepareAsync()
                updateStatus(STATE_INITIALIZED)

            } catch (e: Exception) {
                e.printStackTrace()
                updateStatus(STATE_ERROR)
            }
        }

    }


    override fun setDisplay(surfaceHolder: SurfaceHolder?) {
        try {
            mediaPlayer.setDisplay(surfaceHolder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setSurface(surface: Surface?) {
        try {
            LogUtils.e(
                TAG,
                "setDisplay=====>>$surface  &&${surface?.isValid}  mediaPlayer:$mediaPlayer"
            )
            this.surface = surface
            mediaPlayer.setSurface(surface)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getDataSource(): DataSource? = dataSource

    override fun prepareAsync() {
        try {
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun start() {
        try {
            if (getPlayState() == STATE_PREPARED || getPlayState() == STATE_PAUSED || getPlayState() == STATE_PLAYBACK_COMPLETE) {

                mediaPlayer.start()
                updateStatus(STATE_PLAYING)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun pause() {
        try {
            if (getPlayState() == STATE_PLAYING) {
                mediaPlayer.pause()

                updateStatus(STATE_PAUSED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun resume() {
        if (getPlayState() == STATE_PAUSED) {
            mediaPlayer.start()
            updateStatus(STATE_PLAYING)
        }
    }

    override fun seekTo(position: Int) {
        try {
            if (position > 0 && (getPlayState() == STATE_PREPARED ||
                        getPlayState() == STATE_PLAYING ||
                        getPlayState() == STATE_PAUSED ||
                        getPlayState() == STATE_PLAYBACK_COMPLETE)
            ) {

                mediaPlayer.seekTo(position)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun stop() {
        try {
            if (getPlayState() == STATE_PREPARED ||
                getPlayState() == STATE_PLAYING ||
                getPlayState() == STATE_PAUSED ||
                getPlayState() == STATE_PLAYBACK_COMPLETE
            ) {

                mediaPlayer.stop()

                updateStatus(STATE_STOPPED)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun reset() {
        mediaPlayer.reset()
        updateStatus(STATE_IDLE)
    }

    override fun release() {
        resetListener()
        mediaPlayer.release()
        updateStatus(STATE_END)
    }


    override fun isPlaying(): Boolean = mediaPlayer.isPlaying

    override fun setVolume(var1: Float, var2: Float) {
        mediaPlayer.setVolume(var1, var2)
    }

    override fun setSpeed(var1: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mediaPlayer.playbackParams = mediaPlayer.playbackParams.apply { speed = var1 }

            if (var1 <= 0) {
                pause()
            } else if (var1 > 0 && getPlayState() === STATE_PAUSED) {
                resume()
            }
        } else {
            Log.e("", "not support play speed setting.")
        }
    }

    override fun getVideoWidth(): Int = mediaPlayer.videoWidth

    override fun getVideoHeight(): Int = mediaPlayer.videoHeight

    override fun getCurrentPosition(): Long {
        try {
            return mediaPlayer.currentPosition.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    override fun getDuration(): Long {
        try {
            return mediaPlayer.duration.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    override fun getAudioSectionId(): Int {
        try {
            return mediaPlayer.audioSessionId
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    override fun resetListener() {
        super.resetListener()

        mediaPlayer.run {
            setOnPreparedListener(null)
            setOnBufferingUpdateListener(null)
            setOnVideoSizeChangedListener(null)
            setOnCompletionListener(null)
            setOnInfoListener(null)
            setOnErrorListener(null)
            setOnTimedTextListener(null)
        }
    }

    private val mOnPreparedListener: MediaPlayer.OnPreparedListener =
        MediaPlayer.OnPreparedListener {
            updateStatus(STATE_PREPARED)
            if (currentState == STATE_PLAYING) {
                start()
            }
            LogUtils.e(TAG, "onPrepared====>>videoWidth :$mVideoWidth  videoHeight :$mVideoHeight")
            notifyOnPrepared()
        }

    private val mOnBufferingUpdateListener = MediaPlayer.OnBufferingUpdateListener { mp, percent ->
        notifyOnBufferingUpdate(percent)
    }

    private val mOnVideoSizeChangedListener =
        MediaPlayer.OnVideoSizeChangedListener { mp, width, height ->
            this@SystemMediaPlayer.mVideoWidth = width
            this@SystemMediaPlayer.mVideoHeight = height
            notifyOnVideoSizeChanged(width, height, 0, 0)
        }

    private val mOnCompletionListener =
        MediaPlayer.OnCompletionListener {
            updateStatus(STATE_PLAYBACK_COMPLETE)
            notifyOnCompletion()
        }


    private val mOnErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->

        updateStatus(STATE_ERROR)

        LogUtils.e(TAG, "OnError=====what:$what    extra :$extra")

        when (what) {
            MediaPlayer.MEDIA_ERROR_IO -> {

            }
            MediaPlayer.MEDIA_ERROR_MALFORMED -> {

            }
            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> {

            }
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> {

            }
            MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> {

            }
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {

            }
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK -> {

            }
        }

        notifyOnError(what, extra) as Boolean
    }

    private val mOnInfoListener = MediaPlayer.OnInfoListener { mp, what, extra ->
        LogUtils.d(TAG, "OnInfo====>>what :$what  extra :$extra")
        when (what) {
            MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_VIDEO_TRACK_LAGGING")
            }
            MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_VIDEO_RENDERING_START")

            }
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_BUFFERING_START")

            }
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_BUFFERING_END")

            }
            MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_BAD_INTERLEAVING")

            }
            MediaPlayer.MEDIA_INFO_NOT_SEEKABLE -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_NOT_SEEKABLE")
            }
            MediaPlayer.MEDIA_INFO_METADATA_UPDATE -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_METADATA_UPDATE")

            }
            MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_UNSUPPORTED_SUBTITLE")

            }
            MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT -> {
                LogUtils.e(TAG, "OnInfo====>>MEDIA_INFO_SUBTITLE_TIMED_OUT")

            }
            MEDIA_INFO_NETWORK_BANDWIDTH -> {

            }

        }

        notifyOnInfo(what, extra) as Boolean
    }

    private val mOnTimedTextListener = MediaPlayer.OnTimedTextListener { mp, text -> }


    override fun updateStatus(state: Int) {
        super.updateStatus(state)

    }

}