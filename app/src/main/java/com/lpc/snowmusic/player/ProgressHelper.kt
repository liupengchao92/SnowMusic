package com.lpc.snowmusic.player

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Author: liupengchao
 * Date: 2021/5/7
 * ClassName :ProgressHelper
 * Desc:进度更新帮助类
 */
class ProgressHelper(val palyer: MusicPlayerEngine) {

    //进度定时任务
    private var progressTask: ProgressTask? = null
    //定时器
    private var progressTimer: Timer? = null


    companion object {

        //监听的进度
        private var listenerList: MutableList<PlayProgressListener> = mutableListOf()

        //添加监听
        fun addProgressListener(listener: PlayProgressListener) {
            listenerList.add(listener)
        }

        //移除监听
        fun removeProgressListener(listener: PlayProgressListener) {
            listenerList.remove(listener)
        }

    }

    /**
     * 进度定时任务
     *
     * */
    inner class ProgressTask : TimerTask() {
        override fun run() {
            //切换到主线程
            GlobalScope.launch(Dispatchers.Main) {
                if (palyer.isPlaying()) {
                    listenerList.forEach {
                        it.onProgressUpdate(palyer.getCurrentPosition(), palyer.getDuration())
                    }
                }
            }
        }
    }

    /**
     * 开始任务
     * */
    fun startProgressTask() {
        cancelProgressTask()
        progressTask = ProgressTask()
        progressTimer = Timer().apply {
            schedule(progressTask, 0, 500)
        }
    }

    /**
     * 取消任务
     * */
    fun cancelProgressTask() {
        if (progressTask != null) {
            progressTask?.cancel()
            progressTask = null
        }

        if (progressTimer != null) {
            progressTimer?.cancel()
            progressTimer = null
        }
    }
}