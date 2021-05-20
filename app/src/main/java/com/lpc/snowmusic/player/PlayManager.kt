package com.lpc.snowmusic.player

import android.app.Activity
import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.os.IBinder
import android.os.RemoteException
import com.lpc.snowmusic.IMusicService
import com.lpc.snowmusic.bean.Music
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/4/22
 * ClassName :PlayManager
 * Desc:
 */
object PlayManager {

    var mService: IMusicService? = null

    //
    private val connectionMap: WeakHashMap<Context, ServiceBinder> by lazy {

        WeakHashMap<Context, ServiceBinder>()
    }

    /**
     * 绑定服务
     *
     */
    fun bindToService(context: Context, callback: ServiceConnection): ServiceToken? {
        //获取Activity
        var realActivity: Activity = if ((context as Activity).isChild) context.parent else context

        ContextWrapper(realActivity).run {
            //启动服务
            startService(Intent(this, MusicPlayerService::class.java))
            //ServiceBinder
            val serviceBinder = ServiceBinder(callback, context)
            //绑定服务
            if (bindService(Intent(this, MusicPlayerService::class.java), serviceBinder, BIND_AUTO_CREATE)) {
                //存储（Context,ServiceConnection）
                connectionMap[this] = serviceBinder
                return ServiceToken(this)
            }
        }
        return null
    }

    /**
     *解除绑定
     *
     */
    fun unbindFromService(token: ServiceToken) {

        token?.let {
            //获取ServiceBinder
            val serviceBinder = connectionMap[token.wrapperContext]
            //解除绑定
            token.wrapperContext.unbindService(serviceBinder)
            //是否全部解除绑定
            if (connectionMap.isNullOrEmpty()) {
                mService = null
            }
        }
    }

    /**
     *播放列表音乐
     *
     */
    fun play(musicList: List<Music>, id: Int, pid: String) {
        try {
            mService?.playPlaylist(musicList, id, pid)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     *播放音乐
     *
     */
    fun play(position: Int) {
        try {
            mService?.play(position)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     *获取正在播放的音乐
     *
     */
    fun getPlayingMusic(): Music? {
        try {
            return mService?.playingMusic
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     *获取歌曲列表
     *
     */
    fun getPlayList(): MutableList<Music> {

        try {
            return mService?.playList!!
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return mutableListOf()
    }

    /**
     *是否正在播放
     *
     */
    fun isPlaying(): Boolean {
        try {
            return mService?.isPlaying!!
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     *播放的位置
     *
     */
    fun position(): Int {
        try {
            return mService?.position() as Int
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     *修改进度
     *
     */
    fun seekTo(msec: Int) {
        mService?.seekTo(msec)
    }

    /**
     *播放或者暂停
     *
     */
    fun playAndPause() {
        try {
            mService?.playPause()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     *上一首
     *
     */
    fun playPrev() {
        try {
            mService?.prev()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     *下一首
     *
     */
    fun playNext() {
        try {
            mService?.next()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun getAudioSessionId(): Int {
        try {
            mService?.AudioSessionId()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return 0
    }


    fun removeFromQueue(position: Int) {
        try {
            mService?.removeFromQueue(position)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun clearPlayQueue() {
        try {
            mService?.clearQueue()
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    class ServiceBinder(private val callback: ServiceConnection, private val context: Context) :
        ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            callback.onServiceDisconnected(name)
            mService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = IMusicService.Stub.asInterface(service)
            callback.onServiceConnected(name, service)
        }
    }

}