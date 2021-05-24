package com.lpc.snowmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.king.view.circleprogressview.CircleProgressView
import com.lpc.snowmusic.R

/**
 * Author: liupengchao
 * Date: 2021/5/24
 * ClassName :PlayPauseView
 * Desc:播放暂停按钮
 */
class PlayPauseView constructor(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    //圆形进度条
    private var progressBar: CircleProgressView? = null
    //播放与暂停
    private var playPauseTv: ImageView? = null

    init {
        initView(context)
    }

    fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_play_pause, this, true)
        progressBar = findViewById(R.id.progressBar)
        playPauseTv = findViewById(R.id.playPauseIv)
    }

    /**
     * 更新播放状态
     *
     * */
    fun updatePlayState(isPlaying: Boolean) {
        if (isPlaying) {
            playPauseTv?.setImageResource(R.drawable.ic_pause_grey)
        } else {
            playPauseTv?.setImageResource(R.drawable.ic_play_grey)
        }
    }

    /**
     * 更新播放进度
     *
     * */
    fun updateProgress(progress: Int, max: Int) {
        progressBar?.progress = progress
        progressBar?.max = max
    }
}