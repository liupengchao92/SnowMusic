package com.example.lpc.videoplayer.video.view

import android.view.View
import com.example.lpc.videoplayer.video.view.listener.SurfaceListener

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :IRenderView
 * Desc:
 */
interface IRenderView {

    companion object {
        //use TextureView for render
        const val RENDER_TYPE_TEXTURE_VIEW = 0

        //use SurfaceView for render
        const val RENDER_TYPE_SURFACE_VIEW = 1
    }


    fun getRenderView(): View

    fun getSizeWidth(): Int

    fun getSizeHeight(): Int

    fun setRenderMode(mode: Int)

    fun setOnSurfaceListener(surfaceListener: SurfaceListener)


}