package com.lpc.snowmusic.player

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.ui.discover.PlayerActivity
import java.lang.ref.WeakReference

/**
 * Author: liupengchao
 * Date: 2021/6/17
 * ClassName :NotifyManager
 * Desc:
 */
class NotifyManager(var context: Context, musicPlayerService: MusicPlayerService) {

    companion object {
        //频道ID
        const val CHANNEL_ID = "snow_music_channelId"

        //频道名称
        const val CHANNEL_NAME = "雪音乐"

        //通知ID
        const val NOTIFICATION_ID = 0x123
    }

    //MusicPlayerService
    private val service: WeakReference<MusicPlayerService> by lazy {
        WeakReference<MusicPlayerService>(
            musicPlayerService
        )
    }

    //通知栏
    private val notificationManager by lazy { context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager }
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notification: Notification? = null

    /**
     * 初始化通知栏
     *
     * */
    fun initNotification() {
        //初始化通知栏渠道
        initNotificationChannel()
        //点击意图
        val playingIntent = Intent(context, PlayerActivity::class.java)
        playingIntent.action = Constants.DEFAULT_NOTIFICATION
        val pendingIntent =
            PendingIntent.getActivity(context, 0, playingIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //NotificationCompat类来处理新旧系统中的兼容方案：
        notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            service.get()?.playingMusic?.run {
                //设置标题
                setContentTitle(title)
                //设置内容
                setContentText(if (artist.isNullOrEmpty()) album else artist)
            }
            //设置SmallIcon
            setSmallIcon(R.drawable.ic_music)
            //设置通知时间
            setWhen(System.currentTimeMillis())
            //设置可见状态，VISIBILITY_PUBLIC屏幕锁定时可见
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            //设置点击意图
            setContentIntent(pendingIntent)
        }
    }

    private fun initNotificationChannel() {
        //从Android 8.0系统开始，Google引入了通知渠道这个概念
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //实例化渠道
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            //描述
            channel.description = "通知栏播放控制"
            //是否有呼吸灯
            channel.enableLights(false)
            //是否有震动
            channel.enableVibration(false)
            //创建渠道
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * 更新状态栏通知
     *
     * @param isChange 是否改变歌曲信息
     */
    fun updateNotification(playingMusic: Music?, isChange: Boolean = false) {
        if (isChange) {
            playingMusic?.let {
                GlideUtils.loadImageBitmap(context, it.coverUri, object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap, p1: Transition<in Bitmap>?) {
                        notificationBuilder?.setLargeIcon(bitmap)
                        notification = notificationBuilder?.build()
                        notificationManager.notify(NOTIFICATION_ID, notification)
                    }

                    override fun onLoadCleared(p0: Drawable?) {

                    }
                })

                notificationBuilder?.run {
                    //设置标题
                    setContentTitle(it.title)
                    //设置内容
                    setContentText(it?.run { if (artist.isNullOrEmpty()) album else artist })
                }
            }
        }


        notification = notificationBuilder?.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
        //设置前台服务
        service.get()?.startForeground(NOTIFICATION_ID, notification)
    }

    /**
     * 取消通知栏
     * */
    fun cancelNotification() {
        service.get()?.stopForeground(true)
        notificationManager.cancel(NOTIFICATION_ID)
    }

}