package com.lpc.snowmusic.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Author: liupengchao
 * Date: 2021/4/14
 * ClassName :ActivityStack
 * Desc:
 */
object ActivityStack {

    fun registerActivityLifecycle(application: Application) {

        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
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