package com.example.lpc.videoplayer.video.view.listener

import android.view.Surface

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :SurfaceListener
 * Desc:
 */
interface SurfaceListener {

    fun onSurfaceAvailable(surface: Surface)

    fun onSurfaceSizeChanged(surface: Surface,width:Int,height:Int)

    fun onSurfaceDestroyed(surface: Surface):Boolean

    fun onSurfaceUpdated(surface: Surface)
}