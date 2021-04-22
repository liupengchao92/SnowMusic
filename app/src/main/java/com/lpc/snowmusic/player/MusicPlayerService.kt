package com.lpc.snowmusic.player

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils

/**
 * Author: liupengchao
 * Date: 2020/5/31
 * ClassName :MusicPlayerService
 * Desc:播放服务 Service
 */


class MusicPlayerService : Service() {

    companion object {
        const val TAG = "MusicPlayerService"
    }

    //
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
}