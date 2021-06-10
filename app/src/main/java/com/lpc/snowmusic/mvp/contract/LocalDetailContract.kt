package com.lpc.snowmusic.mvp.contract

import android.content.Context
import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.bean.LocalArtist
import com.lpc.snowmusic.bean.Music

/**
 * Author: liupengchao
 * Date: 2021/6/8
 * ClassName :LocalDetailContract
 * Desc:
 */
interface LocalDetailContract {

    interface View : IView {

        fun showSongList(songList: MutableList<Music>)

        fun showArtist(artist: MutableList<LocalArtist>)

        fun showAlbum(albums: MutableList<LocalAlbum>)
    }

    interface Presenter : IPresenter<View> {

        fun getLocalSongs(context: Context)

        fun getLocalArtist(context: Context)

        fun getLocalAlbum(context: Context)

    }

    interface Model : IModel

}