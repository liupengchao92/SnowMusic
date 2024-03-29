package com.example.lpc.videoplayer.video.utils

import android.util.Log

/**
 * Author: liupengchao
 * Date: 2021/7/14
 * ClassName :LogUtils
 * Desc:日志工具类
 */
object LogUtils {

    private const val TAG: String = "PLAYER_LOG"

    var LOG_OPEN = true

    @JvmStatic
    fun d(message: String?) {
        if (LOG_OPEN) {
            d(TAG, message)
        }
    }
    @JvmStatic
    fun d(tag: String?, message: String?) {
        if (LOG_OPEN) {
            Log.d(tag, message!!)
        }
    }

    @JvmStatic
    fun w(message: String?) {
        if (LOG_OPEN) {
            w(TAG, message)
        }
    }

    @JvmStatic
    fun w(tag: String?, message: String?) {
        if (LOG_OPEN) {
            Log.w(tag, message!!)
        }
    }
    @JvmStatic
    fun e(message: String?) {
        if (LOG_OPEN) {
            Log.e(TAG, message!!)
        }
    }

    fun e(tag: String?, message: String?) {
        if (LOG_OPEN) {
            Log.e(tag, message!!)
        }
    }

}