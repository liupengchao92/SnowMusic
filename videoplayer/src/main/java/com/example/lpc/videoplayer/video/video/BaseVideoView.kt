package com.example.lpc.videoplayer.video.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import com.example.lpc.videoplayer.R
import com.example.lpc.videoplayer.video.utils.LogUtils
import com.example.lpc.videoplayer.video.view.LPCTextureRenderView

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :BaseVideoView
 * Desc:
 */
 open class BaseVideoView : LPCTextureRenderView {

    private var videoView: View? = null

    private val surfaceViewContainer: ViewGroup by lazy { videoView?.findViewById<View>(R.id.surface_container) as ViewGroup }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(context)
    }

    private fun init(context: Context) {
        videoView = LayoutInflater.from(context).inflate(getLayoutResId(), this, true)
    }

    open fun getLayoutResId(): Int = R.layout.layout_video_base


    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun setDisplay(var1: Surface?) {
        LogUtils.e("setDisplay=====>>")

    }

    override fun releaseSurface(var1: Surface?) {

    }
}