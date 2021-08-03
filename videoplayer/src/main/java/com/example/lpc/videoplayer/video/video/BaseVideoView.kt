package com.example.lpc.videoplayer.video.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.lpc.videoplayer.R
import com.example.lpc.videoplayer.video.entity.DataSource
import com.example.lpc.videoplayer.video.listener.MediaPlayerListener
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

    private val surfaceViewContainer: ViewGroup by lazy { videoView?.findViewById<View>(R.id.surface_container) as ViewGroup }

    //播放按钮
    protected var startIv: ImageView? = null


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
        rootView?.let {
            startIv = it.findViewById(R.id.startIv)
            startIv?.setOnClickListener(this)
        }

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
    }

    open fun pause() {
        playerManager?.pause()
    }

    open fun stop() {
        playerManager?.stop()
    }

    open fun release() {
        playerManager?.release()
    }


    override fun setDisplay(var1: Surface?) {
        LogUtils.e("BaseVideoView setDisplay========>>")
        playerManager?.setDisplay(var1)

    }

    override fun releaseSurface(var1: Surface?) {

    }

    override fun onPrepared() {
        LogUtils.e("onPrepared=======>>")
    }

    override fun onCompletion() {
        LogUtils.e("onCompletion=======>>")

    }

    override fun onBufferingUpdate(var1: Int) {
        LogUtils.e("onBufferingUpdate=======>>")
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
}