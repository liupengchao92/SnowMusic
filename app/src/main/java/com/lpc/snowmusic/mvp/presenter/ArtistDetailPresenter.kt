package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.mvp.contract.ArtistDetailContract
import com.lpc.snowmusic.mvp.model.ArtistDetailModel

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistDetailPresenter
 * Desc:
 */
class ArtistDetailPresenter : BasePresenter<ArtistDetailContract.View, ArtistDetailContract.Model>(),
    ArtistDetailContract.Presenter {

    override fun createModel(): ArtistDetailContract.Model? = ArtistDetailModel()

    override fun loadArtistSongs(vendor: String, id: String, offset: Int, limit: Int) {
        model?.loadArtistSongs(vendor, id, offset)?.request(model, view as IView, true) {
            view?.run {
                showSongs(it.songs)
                showArtistInfo(it)
            }
        }
    }

}