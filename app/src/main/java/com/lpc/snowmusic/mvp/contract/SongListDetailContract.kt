package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.bean.PlaylistDetail
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/19
 * ClassName :SongListDetailContract
 * Desc:
 */
interface SongListDetailContract {

    interface View : IView {

        fun showSongListDetail(playlist: Playlist)

        fun showSongList(mutableList: MutableList<Music>)
    }

    interface Presenter : IPresenter<View> {
        fun getSongListDetail(id: String)
    }

    interface Model : IModel {
        fun getSongListDetail(id: String): Observable<PlaylistDetail>
    }
}