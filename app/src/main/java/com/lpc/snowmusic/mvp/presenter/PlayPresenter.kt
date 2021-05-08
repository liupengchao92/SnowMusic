package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.player.PlayProgressListener
import com.lpc.snowmusic.player.ProgressHelper

/**
 * Author: liupengchao
 * Date: 2021/5/7
 * ClassName :PlayPresenter
 * Desc:
 */
class PlayPresenter : BasePresenter<PlayContract.View, PlayContract.Model>(), PlayContract.Presenter,
    PlayProgressListener {


    override fun attachView(view: PlayContract.View) {
        super.attachView(view)
        ProgressHelper.addProgressListener(this)
    }

    override fun detachView() {
        super.detachView()
        ProgressHelper.removeProgressListener(this)
    }


    override fun getPlayingMusic() {
        //获取正在播放的音乐
        PlayManager.getPlayingMusic()?.let {
            view?.showPlayingMusic(it)
        }

    }

    override fun onProgressUpdate(position: Long, duration: Long) {
        view?.updateProgress(position, duration)
    }
}