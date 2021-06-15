package com.lpc.snowmusic.mvp.presenter

import android.content.Context
import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.database.loader.PlayLocalLoader
import com.lpc.snowmusic.mvp.contract.LocalDetailContract
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Author: liupengchao
 * Date: 2021/6/8
 * ClassName :LocalDetailPresenter
 * Desc:
 */
class LocalDetailPresenter : BasePresenter<LocalDetailContract.View, LocalDetailContract.Model>(),
    LocalDetailContract.Presenter {

    override fun getLocalSongs(context: Context) {
        doAsync {
            val songList = PlayLocalLoader.getLocalMusic(context)
            uiThread {
                view?.showSongList(songList)
            }
        }
    }

    override fun getLocalArtist(context: Context) {
        doAsync {
            var artistList = PlayLocalLoader.getLocalArtist(context)
            uiThread {
                view?.showArtist(artistList)
            }
        }
    }

    override fun getLocalAlbum(context: Context) {
        doAsync {
            var albumList = PlayLocalLoader.getLocalAlbum(context)
            uiThread {
                view?.showAlbum(albumList)
            }
        }
    }
}