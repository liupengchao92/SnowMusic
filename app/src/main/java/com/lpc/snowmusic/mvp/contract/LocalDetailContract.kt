package com.lpc.snowmusic.mvp.contract

import android.content.Context
import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
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
    }

    interface Presenter : IPresenter<View> {

        fun getLocalSongs(context: Context)

    }

    interface Model : IModel

}