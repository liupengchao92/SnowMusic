package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.mvp.contract.MyMusicContract
import com.lpc.snowmusic.player.PlayHistoryLoader
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Author: liupengchao
 * Date: 2021/5/25
 * ClassName :MyMusicPresenter
 * Desc:
 */
class MyMusicPresenter : BasePresenter<MyMusicContract.View, MyMusicContract.Model>(),
    MyMusicContract.Presenter {


    override fun loadSongs() {
        //本地歌曲
        loadLocalList()
        //加载历史
        loadHistoryList()

    }

    fun loadLocalList() {

    }

    fun loadHistoryList() {
        //获取播放历史
        doAsync {
            PlayHistoryLoader.getHistoryList().let {
                if (it.isNotEmpty()) {
                    var history = it
                    uiThread {
                        view?.showHistory(history)
                    }
                }
            }
        }
    }

    fun loadFavoriteList() {

    }

    fun loadLocalMvList() {

    }

    fun loadDownloadList() {

    }

    override fun loadPlaylist(playlist: Playlist?) {

    }
}