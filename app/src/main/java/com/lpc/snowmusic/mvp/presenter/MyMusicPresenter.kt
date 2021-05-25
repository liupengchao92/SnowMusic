package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.mvp.contract.MyMusicContract
import com.lpc.snowmusic.player.PlayHistoryLoader

/**
 * Author: liupengchao
 * Date: 2021/5/25
 * ClassName :MyMusicPresenter
 * Desc:
 */
class MyMusicPresenter : BasePresenter<MyMusicContract.View, MyMusicContract.Model>(), MyMusicContract.Presenter {


    override fun loadSongs() {
        //本地歌曲
        loadLocalList()
        //加载历史
        loadHistoryList()

    }

    private fun loadLocalList() {

    }

    private fun loadHistoryList() {
        //获取播放历史
        PlayHistoryLoader.getHistoryList().let {
            if (!it.isEmpty()) {
                view?.showHistory(it)
            }
        }
    }

    private fun loadFavoriteList() {

    }

    private fun loadLocalMvList() {

    }

    private fun loadDownloadList() {

    }

    override fun loadPlaylist(playlist: Playlist?) {

    }
}