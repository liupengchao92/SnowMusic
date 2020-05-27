package com.lpc.snowmusic.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlin.properties.Delegates

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :MusicApplication
 * Desc:Application
 */
class MusicApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        LogUtils.e("onCreate=============>>")


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                LogUtils.e("Activity=====>>${p0.javaClass.name}")
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }
        })
    }
}