package com.example.lpc.videoplayer.video.view

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.View
import com.example.lpc.videoplayer.video.view.listener.SurfaceListener

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :RenderSurfaceView
 * Desc:
 */
class LPCSurfaceView : SurfaceView, IRenderView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        TODO("Not yet implemented")
    }

    override fun getRenderView(): View {
        TODO("Not yet implemented")
    }

    override fun getSizeWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getSizeHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun setRenderMode(mode: Int) {
        TODO("Not yet implemented")
    }

    override fun setOnSurfaceListener(surfaceListener: SurfaceListener) {
        TODO("Not yet implemented")
    }
}