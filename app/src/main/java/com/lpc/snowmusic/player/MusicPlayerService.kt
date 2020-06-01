package com.lpc.snowmusic.player

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils

/**
 * Author: liupengchao
 * Date: 2020/5/31
 * ClassName :MusicPlayerService
 * Desc:播放服务 Service
 */

const val TAG = "MusicPlayerService"
class MusicPlayerService : Service() {


    private val binder: IBinder = IMusicServiceStub(this)

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        LogUtils.e(TAG,"onCreate")

        super.onCreate()
    }
}