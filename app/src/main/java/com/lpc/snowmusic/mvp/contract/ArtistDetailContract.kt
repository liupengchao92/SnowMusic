package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Album
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.Music
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistDetailContract
 * Desc:
 */
interface ArtistDetailContract {

    interface View : IView {
        //显示歌曲列表
        fun showSongs(songList: MutableList<Music>)

        //显示歌手信息
        fun showArtistInfo(artist: Artist)

        //显示歌手的所有专辑
        fun showAllAlbum(albumList: MutableList<Album>)
    }

    interface Presenter : IPresenter<View> {

        fun loadArtistSongs(vendor: String, id: String, offset: Int = 0, limit: Int = 50)

    }

    interface Model : IModel {
        fun loadArtistSongs(vendor: String, id: String, offset: Int = 0, limit: Int = 50): Observable<Artist>
    }
}