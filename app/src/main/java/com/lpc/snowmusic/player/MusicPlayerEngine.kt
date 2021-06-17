package com.lpc.snowmusic.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.os.PowerManager
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.player.MusicPlayerService.Companion.PLAYER_PREPARED
import com.lpc.snowmusic.player.MusicPlayerService.Companion.PREPARE_ASYNC_UPDATE
import com.lpc.snowmusic.player.MusicPlayerService.Companion.RELEASE_WAKELOCK
import com.lpc.snowmusic.player.MusicPlayerService.Companion.TRACK_PLAY_ENDED
import com.lpc.snowmusic.player.MusicPlayerService.Companion.TRACK_PLAY_ERROR
import com.lpc.snowmusic.player.MusicPlayerService.Companion.TRACK_WENT_TO_NEXT
import java.lang.ref.WeakReference

/**
 * Author: liupengchao
 * Date: 2021/4/28
 * ClassName :MusicPlayerEngine
 * Desc:播放器
 */
class MusicPlayerEngine(musicPlayerService: MusicPlayerService) : MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener {
    //弱引用MusicPlayerService
    private val mService: WeakReference<MusicPlayerService> by lazy {
        WeakReference<MusicPlayerService>(musicPlayerService)
    }
    //MediaPlayer
    private var currentMediaPlayer = MediaPlayer()
    //是否已经初始化
    private var isInitialized = false
    //是否已经准备好
    private var isPrepared = false
    //Handler
    private var handler: Handler? = null

    init {
        //设置唤醒锁，确保MediaPlayer的承载的服务在系统睡眠的时候继续正常运行下去。
        currentMediaPlayer.setWakeMode(mService.get(), PowerManager.PARTIAL_WAKE_LOCK)
    }

    /**
     * 设置播放路径
     * */
    fun setDataSource(path: String) {

        isInitialized = setDataSourceImpl(currentMediaPlayer, path)

    }

    /**
     * 设置播放路径的实现
     * */
    private fun setDataSourceImpl(mediaPlayer: MediaPlayer, path: String): Boolean {
        if (path.isNullOrEmpty()) return false
        try {

            mediaPlayer?.run {
                //正在播放先暂停
                if (isPlaying) stop()
                isPrepared = false
                //重置播放器
                reset()
                //是否缓存
                val cacheSetting = false

                //本地歌曲不需要缓存
                if (path.startsWith("content://") || path.startsWith("/storage")) {
                    setDataSource(mService.get() as Context, Uri.parse(path))
                } else if (cacheSetting) {
                    //TODO 做缓存


                } else {
                    //不缓存
                    setDataSource(path)
                }

                //异步播放
                prepareAsync()
                //设置监听
                setOnErrorListener(this@MusicPlayerEngine)
                setOnPreparedListener(this@MusicPlayerEngine)
                setOnCompletionListener(this@MusicPlayerEngine)
                setOnBufferingUpdateListener(this@MusicPlayerEngine)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * 是否初始化
     * */
    fun isInitlized(): Boolean = isInitialized

    /**
     * 是否已经准备
     * */
    fun isPrepared(): Boolean = isPrepared

    /**
     * 是否正在播放
     * */
    fun isPlaying(): Boolean = currentMediaPlayer.isPlaying

    /**
     * 开始
     * */
    fun start() {
        currentMediaPlayer.start()
    }

    /**
     * 暂停
     * */
    fun pause() {
        currentMediaPlayer.pause()
    }

    /**
     *结束
     * */
    fun stop() {
        try {
            currentMediaPlayer.reset()
            isInitialized = false
            isPrepared = false
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    /**
     *释放
     * */
    fun release() {
        currentMediaPlayer.release()
    }

    /**
     * getDuration 只能在prepared之后才能调用，不然会报-38错误
     * */
    fun getDuration(): Long {
        return if (isPrepared) {
            currentMediaPlayer.duration.toLong()
        } else {
            0
        }
    }

    /**
     * 获取播放进度
     * */
    fun getCurrentPosition(): Long {
        return try {
            currentMediaPlayer.currentPosition.toLong()
        } catch (e: IllegalStateException) {
            -1
        }
    }

    /**
     * 设置进度
     * */
    fun seekTo(msec: Int) {
        currentMediaPlayer.seekTo(msec)
    }

    /**
     * 设置声音大小
     * */
    fun setVolume(volume: Float) {
        try {
            currentMediaPlayer.setVolume(volume, volume)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取audioSessionId
     * */
    fun getAudioSessionId(): Int = currentMediaPlayer.audioSessionId

    /**
     * 设置Handler
     * */
    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    override fun onPrepared(mp: MediaPlayer?) {
        //开始播放
        mp?.start()
        if (!isPrepared) {
            isPrepared = true
            //通知MusicPlayerService
            handler?.let {
                val message: Message = it.obtainMessage(PLAYER_PREPARED)
                it.sendMessage(message)
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        LogUtils.d("onCompletion:${mp == currentMediaPlayer}")
        handler?.run {
            if (mp == currentMediaPlayer) {
                sendEmptyMessage(TRACK_WENT_TO_NEXT)
            } else {
                mService.get()?.wakeLock?.acquire(3000)
                sendEmptyMessage(TRACK_PLAY_ENDED)
                sendEmptyMessage(RELEASE_WAKELOCK)
            }
        }
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        LogUtils.d("onBufferingUpdate:  percent :$percent")

        handler?.run {
            val message = obtainMessage(PREPARE_ASYNC_UPDATE, percent)
            sendMessage(message)
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        LogUtils.e("Music Server Error what:  :$what   extra :$extra")
        return when (what) {
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> mediaPlayerError()
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> mediaPlayerError()
            else -> false
        }
    }

    /**
     * 播放器错误
     * */
    private fun mediaPlayerError(): Boolean {
        val service = mService.get()
        val errorInfo = TrackErrorInfo(service?.getAudioId(), service?.getTitle())
        isInitialized = false
        //播放错误，需要重新释放mediaPlayer
        currentMediaPlayer.release()
        currentMediaPlayer = MediaPlayer()
        currentMediaPlayer.setWakeMode(service, PowerManager.PARTIAL_WAKE_LOCK)

        //通知MusicPlayerService
        handler?.run {
            val msg = obtainMessage(TRACK_PLAY_ERROR, errorInfo)
            sendMessageDelayed(msg, 500)
        }

        return true
    }


}