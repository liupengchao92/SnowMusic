package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.mvp.contract.SongListDetailContract
import com.lpc.snowmusic.mvp.model.SongListDetailModel
import com.lpc.snowmusic.utils.MusicUtils

/**
 * Author: liupengchao
 * Date: 2021/4/19
 * ClassName :SongListDetailPresenter
 * Desc:
 */
class SongListDetailPresenter : BasePresenter<SongListDetailContract.View, SongListDetailContract.Model>(),
    SongListDetailContract.Presenter {

    override fun createModel(): SongListDetailContract.Model? = SongListDetailModel()

    override fun getSongListDetail(id: String) {
        model?.getSongListDetail(id)?.request(model, view as IView, true) {
            it.playlist?.let {
                val playlist = Playlist()
                playlist.pid = it.id.toString()
                playlist.name = it.name
                playlist.coverUrl = it.coverImgUrl
                playlist.des = it.description
                playlist.date = it.createTime
                playlist.updateDate = it.updateTime
                playlist.playCount = it.playCount.toLong()
                playlist.type = Constants.PLAYLIST_WY_ID
                playlist.musicList = MusicUtils.getNeteaseMusicList(it.tracks)

                view?.showSongListDetail(playlist)
                view?.showSongList(playlist.musicList)
            }
        }
    }
}