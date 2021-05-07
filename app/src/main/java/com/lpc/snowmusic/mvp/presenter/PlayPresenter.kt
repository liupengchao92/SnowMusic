package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.player.PlayManager

/**
 * Author: liupengchao
 * Date: 2021/5/7
 * ClassName :PlayPresenter
 * Desc:
 */
class PlayPresenter : BasePresenter<PlayContract.View, PlayContract.Model>(), PlayContract.Presenter {

    override fun getPlayingMusic() {
        //获取正在播放的音乐
        PlayManager.getPlayingMusic()?.let {
            view?.showPlayingMusic(it)
        }

    }
}