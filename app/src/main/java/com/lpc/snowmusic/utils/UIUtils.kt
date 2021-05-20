package com.lpc.snowmusic.utils

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.player.PlayQueueManager

/**
 * Author: liupengchao
 * Date: 2021/5/12
 * ClassName :UIUtils
 * Desc:
 */
object UIUtils {

    /**
     * 防止快速点击却换歌曲
     */
    private var lastClickTime: Long = 0

    @Synchronized
    fun isFastClick(): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 500) {
            return true
        }
        lastClickTime = time
        return false
    }

    /**
     * 改变播放模式
     */
    fun updatePlayMode(imageView: ImageView, isChange: Boolean = false) {
        try {
            var playMode = PlayQueueManager.getPlayModeId()
            if (isChange) playMode = PlayQueueManager.updatePlayMode()
            when (playMode) {
                PlayQueueManager.PLAY_MODE_LOOP -> {
                    imageView.setImageResource(R.drawable.ic_repeat)
                    if (isChange) ToastUtils.showShort(MusicApplication.context.resources.getString(R.string.play_mode_loop))
                }
                PlayQueueManager.PLAY_MODE_REPEAT -> {
                    imageView.setImageResource(R.drawable.ic_repeat_one)
                    if (isChange) ToastUtils.showShort(MusicApplication.context.resources.getString(R.string.play_mode_repeat))

                }
                PlayQueueManager.PLAY_MODE_RANDOM -> {
                    imageView.setImageResource(R.drawable.ic_shuffle)
                    if (isChange) ToastUtils.showShort(MusicApplication.context.resources.getString(R.string.play_mode_random))
                }
            }

            imageView.drawable.setTint(Color.WHITE)
        } catch (e: Throwable) {

        }
    }

    /**
     * 改变播放模式
     */
    fun updatePlayMode(imageView: ImageView, textView: TextView) {
        try {
            var playMode = PlayQueueManager.getPlayModeId()
            when (playMode) {
                PlayQueueManager.PLAY_MODE_LOOP -> {
                    imageView.setImageResource(R.drawable.ic_repeat)
                    textView.text = MusicApplication.context.resources.getString(R.string.play_mode_loop)
                }
                PlayQueueManager.PLAY_MODE_REPEAT -> {
                    imageView.setImageResource(R.drawable.ic_repeat_one)
                    textView.text = MusicApplication.context.resources.getString(R.string.play_mode_repeat)
                }
                PlayQueueManager.PLAY_MODE_RANDOM -> {
                    imageView.setImageResource(R.drawable.ic_shuffle)
                    textView.text = MusicApplication.context.resources.getString(R.string.play_mode_random)
                }
            }
            //修改图标颜色
            imageView.drawable.setTint(Color.BLACK)
        } catch (e: Throwable) {

        }
    }
}