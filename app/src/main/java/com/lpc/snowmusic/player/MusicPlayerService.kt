package com.lpc.snowmusic.player

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.event.MetaChangedEvent
import com.lpc.snowmusic.event.StatusChangedEvent
import com.lpc.snowmusic.http.function.RequestCallBack
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.music.MusicApi
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

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
        //释放屏幕锁
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


        //状态改变(歌曲替换)
        const val META_CHANGED = "com.lpc.snowmusic.meta_changed"
        const val PLAY_STATE_CHANGED = "com.lpc.snowmusic.play_state_change"

    }

    //当前播放的位置
    var playingPos: Int = -1
    //当前正在播放的音乐
    var playingMusic: Music? = null
    //当前播放队列
    var playQueue: MutableList<Music> = mutableListOf()
    //播放历史
    private var historyPos: MutableList<Int> = mutableListOf()
    //是否正在播放
    var isMusicPlaying: Boolean = false
    //歌单类型ID
    private var playListId: String = Constants.PLAYLIST_QUEUE_ID
    //播放器
    lateinit var mediaPlayer: MusicPlayerEngine
    //工作Handler
    lateinit var handler: MusicPlayerHandler
    //主线程Handler
    private lateinit var mainHandler: Handler
    //进度更新
    lateinit var progressHelper: ProgressHelper
    //屏幕锁
    var wakeLock: PowerManager.WakeLock? = null

    private val binder: IBinder = IMusicServiceStub(this)


    inner class MusicPlayerHandler(val service: MusicPlayerService, looper: Looper) : Handler(looper) {
        //弱引用MusicPlayerService
        val mService: WeakReference<MusicPlayerService> by lazy { WeakReference(service) }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtils.d("handleMessage :what ${msg.what}")
            synchronized(mService) {
                when (msg.what) {
                    PLAYER_PREPARED -> {
                        //准备完毕开始播放
                        isMusicPlaying = true
                        updateNotification(false)
                        notifyStateChange(PLAY_STATE_CHANGED)
                    }
                    TRACK_WENT_TO_NEXT -> {
                        //Player播放完毕切换到下一首
                        mainHandler.post { service.next(true) }

                    }
                    TRACK_PLAY_ENDED -> {
                        //Player播放完毕且暂时没有下一首
                        if (PlayQueueManager.getPlayModeId() == PlayQueueManager.PLAY_MODE_REPEAT) {
                            service.seekTo(0)
                            mainHandler.post { service.play() }
                        } else {
                            mainHandler.post { service.next(true) }
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("onCreate=======>>")
        //初始化广播
        initReceiver()
        //初始化参数
        initConfig()
        //初始化电话监听服务
        initTelephony()
        //初始化通知
        initNotification()
        //初始化音乐播放服务
        initMediaPlayer()

    }


    /**
     *初始化广播
     * */
    private fun initReceiver() {

    }

    /**
     *初始化相关配置
     * */
    private fun initConfig() {
        //实例化Handler
        handler = MusicPlayerHandler(this, Looper.getMainLooper())
        //主线程Handler
        mainHandler = Handler(Looper.getMainLooper())
    }

    /**
     * 初始化电话监听服务
     * */
    private fun initTelephony() {

    }

    /**
     * 初始化通知栏
     * */
    private fun initNotification() {

    }

    /**
     * 初始化播放器
     *
     * */
    private fun initMediaPlayer() {
        //播放器
        mediaPlayer = MusicPlayerEngine(this)
        mediaPlayer.setHandler(handler)
        //更新进度
        progressHelper = ProgressHelper(mediaPlayer)
        progressHelper.startProgressTask()

    }

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

    override fun onDestroy() {
        super.onDestroy()
        //停止更新进度任务
        progressHelper.cancelProgressTask()
    }


    /**
     * 获取audioId
     * */
    fun getAudioId(): String? = playingMusic?.mid

    /**
     * 获取音乐的标题
     * */
    fun getTitle(): String? = playingMusic?.title


    /**
     * 播放当前或者下一首
     * */
    private fun playCurrentAndNext() {
        //线程锁
        synchronized(this) {
            if (playingPos >= playQueue.size || playingPos < 0) return
            //获取将要播放的音乐
            playingMusic = playQueue[playingPos]
            //通知状态变更
            notifyStateChange(META_CHANGED)
            //通知栏更新
            updateNotification(true)
            //播放状态
            isMusicPlaying = false
            notifyStateChange(PLAY_STATE_CHANGED)
            updateNotification(false)

            LogUtils.d("playingSongInfo: ${playingMusic.toString()}")
            playingMusic?.let {
                //判断Uri是否可用
                if (it.uri.isNullOrEmpty() || Constants.LOCAL != it.type) {
                    if (!NetworkUtils.isConnected()) {
                        ToastUtils.showShort("网络不可用，请检查网络连接")
                    } else {
                        MusicApi.getMusicInfo(it).request(object : RequestCallBack<Music> {
                            override fun onSuccess(t: Music) {
                                LogUtils.e("音乐URL :${t}")
                                it.uri = t.uri
                                saveHistory()
                                mediaPlayer.setDataSource(t.uri!!)
                            }

                            override fun onError(e: Throwable) {
                                LogUtils.e("播放异常-------->${e.message}")
                            }
                        })
                    }
                } else {

                    if (!it.uri?.startsWith(Constants.HTTP)!! && !FileUtils.isFileExists(it.uri)) {

                    } else {
                        //设置播放地址
                        mediaPlayer.setDataSource(it.uri!!)
                    }
                }
            }
        }
    }

    /**
     * 切换歌单播放
     * 1、歌单不一样切换，不一样不切换
     * 2、相同歌单只切换歌曲
     * 3、相同歌曲不重新播放
     *
     * @param musicList 歌单
     * @param id        歌曲位置id
     * @param pid       歌单id
     */
    fun play(musicList: List<Music>, position: Int, pid: String) {
        if (musicList.size <= position) return
        if (playListId == pid && position == playingPos) return
        if (playListId != pid || playQueue.isEmpty() || playQueue.size != musicList.size) {
            setPlayQueueList(musicList)
            playListId = pid
        }
        playingPos = position
        playCurrentAndNext()
    }

    /**
     *根据位置播放音乐
     * @param position        歌曲位置id
     */
    fun play(position: Int) {
        //获取播放的位置
        playingPos = if (position > playQueue.size || position < 0) {
            PlayQueueManager.getNextPosition(true, playQueue.size, playingPos)
        } else {
            position
        }
        if (playingPos < 0) return
        //播放音乐
        playCurrentAndNext()
    }

    /**
     * 设置播放队列
     *
     * */
    private fun setPlayQueueList(musicList: List<Music>) {
        playQueue.clear()
        historyPos.clear()
        playQueue.addAll(musicList)
        // notifyStateChange(PLAY_QUEUE_CHANGE)
    }

    /**
     * 修改进度
     *
     * */
    fun seekTo(msec: Int) {
        mediaPlayer.seekTo(msec)
    }

    /**
     * 播放或者暂停
     *
     * */
    fun playPause() {
        if (isMusicPlaying) {
            pause()
        } else {
            if (mediaPlayer.isInitlized()) {
                play()
            } else {
                playCurrentAndNext()
            }
        }
    }

    private fun play() {
        if (mediaPlayer.isInitlized()) {
            mediaPlayer.start()
            isMusicPlaying = true
            notifyStateChange(PLAY_STATE_CHANGED)
            updateNotification(false)
        } else {
            playCurrentAndNext()
        }
    }

    private fun pause() {
        if (isMusicPlaying) {
            mediaPlayer.pause()
            isMusicPlaying = false
            notifyStateChange(PLAY_STATE_CHANGED)
            updateNotification(false)
        }
    }

    private fun stop(remove_status_icon: Boolean) {
        if (mediaPlayer != null && mediaPlayer.isInitlized()) {
            mediaPlayer.stop()
        }

        if (remove_status_icon) {

        }

        if (remove_status_icon) {
            isMusicPlaying = false
        }
    }

    fun prev() {
        //获取上一首的位置
        playingPos = PlayQueueManager.getPreviousPosition(playQueue.size, playingPos)
        //停止播放
        stop(false)
        //播放上一首
        playCurrentAndNext()
        //打印位置
        LogUtils.d("上一首的位置 ：$playingPos")

    }

    fun next(isAuto: Boolean) {
        //获取下一首的位置
        playingPos = PlayQueueManager.getNextPosition(isAuto, playQueue.size, playingPos)
        //停止播放
        stop(false)
        //播放上一首
        playCurrentAndNext()
        //打印位置
        LogUtils.d("下一首的位置 ：$playingPos")
    }


    fun getAudioSessionId(): Int = mediaPlayer.getAudioSessionId()

    /**
     * 是否准备播放
     *
     * @return
     */
    fun isPrepared(): Boolean = mediaPlayer.isPrepared()

    /**
     * 发送更新广播
     *
     * @param what 发送更新广播
     */
    private fun notifyStateChange(what: String) {
        LogUtils.d("")
        when (what) {
            PLAY_STATE_CHANGED -> {
                //播放状态发生改变
                EventBus.getDefault().post(StatusChangedEvent(isMusicPlaying, isMusicPlaying, 0))
                //加载歌词
                FloatLyricViewManager.loadLyric(playingMusic)
            }
            META_CHANGED -> {
                //播放的资源发生改变
                EventBus.getDefault().post(MetaChangedEvent(playingMusic!!))

            }
        }
    }

    /**
     * 更新状态栏通知
     *
     * @param isChange 是否改变歌曲信息
     */
    private fun updateNotification(isChange: Boolean) {

    }

    /**
     * 保存播放历史
     *
     */
    private fun saveHistory() {

    }


}