package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Music

/**
 * Author: liupengchao
 * Date: 2021/5/7
 * ClassName :PlayContract
 * Desc:
 */
interface PlayContract {

    interface View : IView {

        fun showPlayingMusic(music: Music)

        fun showLyric(lyric: String?, init: Boolean)

        fun updatePlayStatus(isPlaying: Boolean)

        fun updatePlayMode() {}

        fun updateProgress(progress: Long, max: Long)

    }

    interface Presenter : IPresenter<View> {

        fun getPlayingMusic()
    }

    interface Model : IModel

}