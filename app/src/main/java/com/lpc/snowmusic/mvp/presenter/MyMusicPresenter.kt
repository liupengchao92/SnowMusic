package com.lpc.snowmusic.mvp.presenter

import android.content.Context
import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.database.loader.PlayHistoryLoader
import com.lpc.snowmusic.database.loader.PlayLocalLoader
import com.lpc.snowmusic.mvp.contract.MyMusicContract
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
        doAsync {
            PlayLocalLoader.getLocalMusic(view?.getViewContext() as Context, false).let {
                var local = it
                uiThread {
                    view?.showLocal(local)
                }
            }
        }
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