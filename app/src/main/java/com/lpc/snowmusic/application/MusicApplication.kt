package com.lpc.snowmusic.application

import android.app.Application
import com.blankj.utilcode.util.LogUtils

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :MusicApplication
 * Desc:Application
 */
class MusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.e("onCreate=============>>")

    }
}