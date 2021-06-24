package com.lpc.snowmusic.player

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.event.LyricUpdateEvent
import com.lpc.snowmusic.http.function.RequestCallBack
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.music.MusicApi
import com.lpc.snowmusic.widget.lrcview.FloatLyricView
import com.lpc.snowmusic.widget.lrcview.LyricInfo
import com.lpc.snowmusic.widget.lrcview.LyricParseUtils
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2021/5/14
 * ClassName :FloatLyricViewManager
 * Desc:歌词管理类
 * */
object FloatLyricViewManager {
    //WindowManager
    private val windowManager by lazy { MusicApplication.context.getSystemService(WINDOW_SERVICE) as WindowManager }

    //
    private var windowManagerParams: WindowManager.LayoutParams? = null

    //歌词视图
    private var floatLyricView: FloatLyricView? = null

    //歌词内容
    var lyricContent: String = ""

    //歌词
    private var lyricInfo: LyricInfo? = null

    //歌曲名称
    private var songName = ""

    //第一次设置歌词
    private var isFirstSettingLyric: Boolean = false


    /**
     * 加载歌词
     *
     * */
    fun loadLyric(playingMusic: Music?) {
        playingMusic?.let {
            songName = it.title ?: ""
            MusicApi.getLyricInfo(it)?.request(object : RequestCallBack<String> {
                override fun onSuccess(t: String) {
                    //更新歌词
                    updateLyric(t)
                }

                override fun onError(e: Throwable) {
                    LogUtils.e("${it.title}加载歌词失败")
                }
            })
        }
    }

    /**
     * 更新歌词
     *r
     * @param info 歌词
     */
    private fun updateLyric(lyricString: String) {
        lyricContent = lyricString
        //发送歌词更新时间
        EventBus.getDefault().post(LyricUpdateEvent(lyricString))
        //格式化歌词
        lyricInfo = LyricParseUtils.setLyricResource(lyricString)
        isFirstSettingLyric = true
    }

    /**
     * 创建歌词悬浮窗
     *
     * @param context
     */
    private fun createFloatLyricView(context: Context) {
        try {
            val size = Point()
            //获取屏幕宽高
            windowManager.defaultDisplay.getSize(size)
            //宽
            val screenWidth = size.x
            //高
            val screenHeight = size.y

            if (floatLyricView == null) {
                //创建歌词悬浮窗视图
                floatLyricView = FloatLyricView(context)

                if (windowManagerParams == null) {
                    //创建 WindowManager LayoutParams
                    windowManagerParams = WindowManager.LayoutParams().apply {
                        //设置窗口类型
                        type = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        } else {
                            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                        }
                        format = PixelFormat.RGBA_8888
                        gravity = Gravity.START or Gravity.TOP
                        width = floatLyricView?.viewWidth ?: 0
                        height = floatLyricView?.viewHeight ?: 0
                        x = screenWidth
                        y = screenHeight / 2
                        //设置可触摸可点击
                        flags =
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

                    }
                    //
                    windowManagerParams?.let {
                        windowManager.addView(floatLyricView, it)
                    }
                    //将视图添加到Window
                    LogUtils.d("创建悬浮歌词 创建完成")
                }
            }
        } catch (e: Exception) {
            LogUtils.e("创建歌词悬浮窗失败：${e.message}")
            e.printStackTrace()
        }
    }

    //移除歌词悬浮窗
    fun removeFloatLyricView() {
        try {
            if (floatLyricView != null) {
                windowManager.removeView(floatLyricView)
                floatLyricView = null
                windowManagerParams = null
                LogUtils.d("歌词悬浮窗 移除成功")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //更新悬浮窗歌词
    fun updateFloatLyric(context: Context, position: Long, duration: Long) {
        //主线程中更新UI
        Handler(Looper.getMainLooper()).post(Runnable {
            if (AppUtils.isAppForeground()) {
                //应用在前台不展示歌词悬浮窗
                removeFloatLyricView()
            } else {
                //应用在后台，显示歌词悬浮窗
                if (isWindowShowing()) {
                    //悬浮窗存在
                    floatLyricView?.let {
                        if (isFirstSettingLyric) {
                            //设置歌曲名称
                            it.title?.text = songName
                            //设置歌词
                            it.lyricText?.setLyricInfo(lyricInfo)

                            isFirstSettingLyric = false
                        }
                        //歌词进度
                        it.lyricText?.setCurrentTimeMillis(position)
                        //歌词总时长
                        it.lyricText?.setDurationMillis(duration)

                    }

                } else {
                    //创建悬浮窗
                    LogUtils.d("创建歌词悬浮窗")
                    createFloatLyricView(context)
                }
            }

        })
    }

    //悬浮窗是否显示
    private fun isWindowShowing(): Boolean = floatLyricView != null

}