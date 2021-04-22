package com.lpc.snowmusic.player

import android.app.Activity
import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.os.IBinder
import com.lpc.snowmusic.IMusicService
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