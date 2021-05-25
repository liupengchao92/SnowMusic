package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.Playlist

/**
 * Author: liupengchao
 * Date: 2021/5/25
 * ClassName :MyMusicContract
 * Desc:我的
 */
interface MyMusicContract {

    interface View : IView {

        fun showSongs(songList: MutableList<Music>)

        fun showLocalPlaylist(playlists: MutableList<Playlist>)

        fun showPlaylist(playlists: MutableList<Playlist>)

        fun showWyPlaylist(playlists: MutableList<Playlist>)

        fun showHistory(musicList: MutableList<Music>)

        fun showLoveList(musicList: MutableList<Music>)

        fun showDownloadList(musicList: MutableList<Music>)

    }

    interface Presenter : IPresenter<View> {

        fun loadSongs()

        fun loadPlaylist(playlist: Playlist? = null)
    }

    interface Model : IModel
}