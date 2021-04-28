package com.lpc.snowmusic.player

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.PowerManager
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.bean.Music

/**
 * Author: liupengchao
 * Date: 2020/5/31
 * ClassName :MusicPlayerService
 * Desc:播放服务 Service
 */


class MusicPlayerService : Service() {

    companion object {
        const val TAG = "MusicPlayerService"
        //下一首
        const val TRACK_WENT_TO_NEXT = 2
        //播放完成
        const val RELEASE_WAKELOCK = 3
        //播放完成
        const val TRACK_PLAY_ENDED = 4
        //播放出错
        const val TRACK_PLAY_ERROR = 5
        //PrepareAsync装载进程
        const val PREPARE_ASYNC_UPDATE = 7
        //MediaPlayer准备完成
        const val PLAYER_PREPARED = 8
        //音频焦点改变
        const val AUDIO_FOCUS_CHANGE = 12
        //音量改变减少
        const val VOLUME_FADE_DOWN = 13
        //音量改变增加
        const val VOLUME_FADE_UP = 14
    }

    //当前正在播放的音乐
    var playingMusic: Music? = null
    //
    var wakeLock: PowerManager.WakeLock? = null

    private val binder: IBinder = IMusicServiceStub(this)

    override fun onBind(intent: Intent?): IBinder {
        LogUtils.d("onBind=======>>")
        return binder
    }

    override fun unbindService(conn: ServiceConnection?) {
        super.unbindService(conn)
        LogUtils.d("unbindService=======>>")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        LogUtils.d("onUnbind=======>>")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("onStartCommand=======>>")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onCreate() {
        LogUtils.d("onCreate=======>>")

        super.onCreate()
    }

    /**
     * 获取audioId
     * */
    fun getAudioId(): String? = playingMusic?.mid

    /**
     * 获取音乐的标题
     * */
    fun getTitle(): String? = playingMusic?.title
}