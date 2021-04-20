package com.lpc.snowmusic.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/4/14
 * ClassName :ActivityStack
 * Desc:
 */
object ActivityStack {
    //任务栈
    private val activityStacks = Stack<Activity>()
    //


    fun registerActivityLifecycle(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                //入栈
                activityStacks?.push(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                //移除栈
                activityStacks?.remove(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }
        })
    }

    /**
     * 获取当前的Activity
     * */
    fun currentActivity(): Activity? {
        return if (activityStacks.isNotEmpty()) activityStacks.peek() else null
    }
}