package com.example.lpc.videoplayer.video.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.Surface
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.lpc.videoplayer.video.utils.LogUtils
import com.example.lpc.videoplayer.video.view.listener.SurfaceListener

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :LPCTextureRenderView
 * Desc:
 */
abstract class LPCTextureRenderView : FrameLayout, SurfaceListener {

    private var renderType: Int = IRenderView.RENDER_TYPE_TEXTURE_VIEW

    private var renderView: IRenderView? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

    }


    fun addSurfaceView(surfaceViewContainer: ViewGroup) {
        renderView = if (renderType == IRenderView.RENDER_TYPE_TEXTURE_VIEW) {
            LPCTextureView(context)
        } else {
            LPCSurfaceView(context)
        }

        renderView?.let {
            it.setOnSurfaceListener(this)
            //must set WRAP_CONTENT and CENTER for render aspect ratio and measure.
            val lp =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER)
            surfaceViewContainer.addView(it?.getRenderView(), lp)
        }
    }

    override fun onSurfaceAvailable(surface: Surface) {
        setDisplay(surface)
        LogUtils.d("LPCTextureRenderView=======>>onSurfaceAvailable :$surface")
    }

    override fun onSurfaceSizeChanged(surface: Surface, width: Int, height: Int) {
        LogUtils.d("LPCTextureRenderView=======>>onSurfaceSizeChanged :$surface")

    }

    override fun onSurfaceDestroyed(surface: Surface): Boolean {
        releaseSurface(surface)
        LogUtils.d("LPCTextureRenderView=======>>onSurfaceSizeChanged :$surface")
        return true
    }

    override fun onSurfaceUpdated(surface: Surface) {
        LogUtils.d("LPCTextureRenderView=======>>onSurfaceUpdated :$surface")

    }

    abstract fun setDisplay(var1: Surface?)

    abstract fun releaseSurface(var1: Surface?)
}