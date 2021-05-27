package com.lpc.snowmusic.mvp.presenter

import android.Manifest
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.database.loader.PlayHistoryLoader
import com.lpc.snowmusic.database.loader.PlayLocalLoader
import com.lpc.snowmusic.mvp.contract.MyMusicContract
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
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
            PlayLocalLoader.getLocalMusic(view?.getViewContext() as Context).let {
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