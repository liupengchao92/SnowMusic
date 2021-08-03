package com.example.lpc.videoplayer.video.view

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import android.view.View
import com.example.lpc.videoplayer.video.utils.MeasureHelper
import com.example.lpc.videoplayer.video.view.listener.SurfaceListener

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :LPCTextureView
 * Desc:TextureView
 */
class LPCTextureView : TextureView, IRenderView, TextureView.SurfaceTextureListener,
    MeasureHelper.MeasureFormVideoParamsListener {


    private var mSurface: Surface? = null
    private var measureHelper: MeasureHelper? = null
    private var surfaceListener: SurfaceListener? = null
    private var videoParamsListener: MeasureHelper.MeasureFormVideoParamsListener? = null


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    fun init(context: Context) {
        measureHelper = MeasureHelper(this, this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureHelper?.prepareMeasure(widthMeasureSpec, heightMeasureSpec, rotation.toInt())
        measureHelper?.let {
            setMeasuredDimension(it.measuredWidth, it.measuredHeight)
        }

    }

    override fun getRenderView(): View = this


    override fun getSizeWidth(): Int = width

    override fun getSizeHeight(): Int = height

    override fun setRenderMode(mode: Int) {
        measureHelper?.setAspectRatio(mode)
    }

    override fun setOnSurfaceListener(surfaceListener: SurfaceListener) {
        this.surfaceTextureListener = this
        this.surfaceListener = surfaceListener
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface).apply {
            surfaceListener?.onSurfaceAvailable(this)
        }

    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface?.let {
            surfaceListener?.onSurfaceSizeChanged(it, width, height)
        }

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurface?.let {
            surfaceListener?.onSurfaceDestroyed(it)
        }
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        mSurface?.let {
            surfaceListener?.onSurfaceUpdated(it)
        }
    }


    override fun getCurrentVideoWidth(): Int {
        if (videoParamsListener != null) {
            return videoParamsListener?.currentVideoWidth as Int
        }
        return 0
    }

    override fun getCurrentVideoHeight(): Int {
        if (videoParamsListener != null) {
            videoParamsListener?.currentVideoHeight as Int
        }
        return 0
    }

    override fun getVideoSarNum(): Int {
        if (videoParamsListener != null) {
            videoParamsListener?.videoSarNum as Int
        }
        return 0
    }

    override fun getVideoSarDen(): Int {
        if (videoParamsListener != null) {
            videoParamsListener?.videoSarDen as Int
        }
        return 0
    }

}