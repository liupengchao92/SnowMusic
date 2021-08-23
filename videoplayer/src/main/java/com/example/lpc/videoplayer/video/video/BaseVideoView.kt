package com.example.lpc.videoplayer.video.video

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.lpc.videoplayer.R
import com.example.lpc.videoplayer.video.entity.DataSource
import com.example.lpc.videoplayer.video.listener.MediaPlayerListener
import com.example.lpc.videoplayer.video.player.base.IMediaPlayer
import com.example.lpc.videoplayer.video.utils.CommonUtil
import com.example.lpc.videoplayer.video.utils.LogUtils
import com.example.lpc.videoplayer.video.view.LPCTextureRenderView
import java.io.File
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :BaseVideoView
 * Desc:
 */
open class BaseVideoView : LPCTextureRenderView, MediaPlayerListener, View.OnClickListener {

    private var videoView: View? = null

    private var playerManager: VideoManager? = null

    //播放视图容器
    private val surfaceViewContainer by lazy { videoView?.findViewById<View>(R.id.surface_container) as ViewGroup }

    //播放按钮
    protected val startIv by lazy { videoView?.findViewById<View>(R.id.startIv) as ImageView }

    //图片封面
    protected val thumbRelativeLayout by lazy { videoView?.findViewById<View>(R.id.thumb) as RelativeLayout }

    //底部进度条部分
    protected val bottomLayoutLL by lazy { videoView?.findViewById<View>(R.id.layout_bottom) as LinearLayout }

    //当前时间
    protected val currentTimeTv by lazy { videoView?.findViewById<View>(R.id.current) as TextView }

    //总时间
    protected val totalTimeTv by lazy { videoView?.findViewById<View>(R.id.total) as TextView }

    //进度条
    protected val progressBar by lazy { videoView?.findViewById<View>(R.id.progress) as ProgressBar }


    //计时器
    private var progressTimer: Timer? = null

    //定时任务
    private var progressTimerTask: ProgressTimerTask? = null


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(context)
    }

    private fun init(context: Context) {
        playerManager = VideoManager()
        playerManager?.setMediaPlayerListener(this)

        videoView = createView()
        addSurfaceView(surfaceViewContainer)

        //
        startIv?.setOnClickListener(this)

    }

    private fun createView(): View {
        return LayoutInflater.from(context).inflate(getLayoutResId(), this, true)
    }

    open fun getLayoutResId(): Int = R.layout.layout_video_base


    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    open fun setDataSource(
        url: String,
        isLooping: Boolean = false,
        isCache: Boolean = false,
        rawId: Int = -1,
        assetsPath: String? = null,
        cachePath: File? = null,
        extraData: HashMap<String, String>? = null
    ) {
        val dataSource = DataSource(url).apply {
            this.isLooping = isLooping
            this.isCache = isCache
            this.rawId = rawId
            this.assetsPath = assetsPath
            this.mCachePath = cachePath
            this.extraData = extraData
        }
        playerManager?.setDataSource(dataSource)
    }

    open fun start() {
        playerManager?.start()
        setUiState(IMediaPlayer.STATE_PLAYING)
    }

    open fun pause() {
        playerManager?.pause()
        setUiState(IMediaPlayer.STATE_PAUSED)
    }

    open fun stop() {
        playerManager?.stop()
    }

    open fun release() {
        playerManager?.release()
    }

    open fun getCurrentPosition(): Long? = playerManager?.getCurrentPosition()

    open fun getDuration(): Long? = playerManager?.getDuration();

    override fun setDisplay(var1: Surface?) {
        playerManager?.setDisplay(var1)

    }

    override fun releaseSurface(var1: Surface?) {

    }

    override fun onPrepared() {
        LogUtils.e("onPrepared=======>>")
        setUiState(IMediaPlayer.STATE_PREPARED)

    }

    override fun onCompletion() {
        LogUtils.e("onCompletion=======>>")
        setUiState(IMediaPlayer.STATE_PLAYBACK_COMPLETE)

    }

    override fun onBufferingUpdate(var1: Int) {
        Handler(Looper.getMainLooper()).post {
            setTextAndProgress(var1)
        }
    }

    override fun onSeekComplete() {
    }

    override fun onError(var1: Int, var2: Int) {
        LogUtils.e("onError=======>>")
    }

    override fun onInfo(var1: Int, var2: Int) {
        LogUtils.e("onInfo=======>>")
    }

    override fun onVideoSizeChanged() {
        LogUtils.e("onVideoSizeChanged=======>>")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startIv -> {
                playerManager?.let {
                    if (it.isPlaying()) {
                        pause()
                    } else {
                        start()
                    }
                }

                updatePlayStatus()
            }
        }
    }

    protected open fun updatePlayStatus() {
        playerManager?.let {
            if (it.isPlaying()) {
                startIv?.setImageResource(R.drawable.ic_pause)
            } else {
                startIv?.setImageResource(R.drawable.ic_play)
            }
        }
    }

    //设置UI的状态
    protected open fun setUiState(state: Int) {

        when (state) {

            IMediaPlayer.STATE_IDLE -> {

            }

        }

        resolveUIState(state)
    }

    protected open fun resolveUIState(state: Int) {
        when (state) {

            IMediaPlayer.STATE_IDLE -> {
                changeUiToIdle()
                cancelProgressTask()
            }

            IMediaPlayer.STATE_PREPARED -> {
                changeUiToPrepared()
                startProgressTask()
            }

            IMediaPlayer.STATE_PLAYING -> {
                changeUiToPlaying()
                startProgressTask()
            }

            IMediaPlayer.STATE_PAUSED -> {
                changeUiToPlaying()
            }

            IMediaPlayer.STATE_PLAYBACK_COMPLETE -> {
                changeUiToAutoComplete()
                cancelProgressTask()
            }
        }
    }

    protected fun changeUiToIdle() {

    }

    protected fun changeUiToPrepared() {
        thumbRelativeLayout.visibility = GONE
    }

    protected fun changeUiToPlaying() {
        thumbRelativeLayout.visibility = GONE

    }

    protected fun changeUiToPause() {

    }

    protected fun changeUiToAutoComplete() {

    }


    /**
     * 开始获取进度的任务
     */
    protected open fun startProgressTask() {
        cancelProgressTask()
        progressTimer = Timer().apply {
            progressTimerTask = ProgressTimerTask()
            schedule(progressTimerTask, 0, 1000)
        }
    }

    protected open fun cancelProgressTask() {
        progressTimer?.cancel()
        progressTimerTask?.cancel()
        progressTimer = null
        progressTimerTask = null
    }


    /**
     * 进度的定时任务
     */
    inner class ProgressTimerTask : TimerTask() {
        override fun run() {
            //切换到主线程执行
            Handler(Looper.getMainLooper()).post {
                setTextAndProgress(0)
            }
        }
    }

    /**
     * 设置时间以及进度
     */
    protected fun setTextAndProgress(secProgress: Int) {
        val position: Int = getCurrentPosition()?.toInt() ?: 0
        val duration: Int = getDuration()?.toInt() ?: 0
        val progress: Int = (position * 100) / if (duration == 0) 1 else duration
        setProgressAndTime(progress, secProgress, position, duration)
    }

    protected fun setProgressAndTime(
        progress: Int,
        secProgress: Int,
        currentTime: Int,
        totalTime: Int
    ) {
        progressBar.progress = progress
        progressBar.secondaryProgress = secProgress
        currentTimeTv.text = CommonUtil.stringForTime(currentTime)
        totalTimeTv.text = CommonUtil.stringForTime(totalTime)

    }
}