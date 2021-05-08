package com.lpc.snowmusic.player

/**
 * Author: liupengchao
 * Date: 2021/5/8
 * ClassName :PlayProgressListener
 * Desc:
 */
interface PlayProgressListener {

    fun onProgressUpdate(position: Long, duration: Long)

}