package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import com.lpc.snowmusic.mvp.model.DiscoverModel

/**
 * Author: liupengchao
 * Date: 2020/9/27
 * ClassName :DiscoverPresenter
 * Desc:
 */
open class DiscoverPresenter : BasePresenter<DiscoverContract.View, DiscoverContract.Model>(),
    DiscoverContract.Presenter {

    override fun createModel(): DiscoverContract.Model? = DiscoverModel()

    override fun loadBannerView() {
        model?.loadBanner()?.request(model, view as IView, true) {
            view?.showBannerView(it.banners)
        }
    }

    override fun getHotSinger(offset: Int, limit: Int) {
        model?.getHotSinger(offset, limit)?.request(model, view as IView) {
            val list = mutableListOf<Artist>()
            it.list.artists?.forEach {
                val playlist = Artist()
                playlist.artistId = it.id.toString()
                playlist.name = it.name
                playlist.picUrl = it.picUrl
                playlist.score = it.score
                playlist.musicSize = it.musicSize
                playlist.albumSize = it.albumSize
                playlist.type = Constants.NETEASE
                list.add(playlist)
            }
            view?.showHotSinger(list)
        }
    }

    override fun getRecommend() {
        model?.getRecommend()?.request(model, view as IView) {
            view?.showRecommendList(it.result!!)
        }
    }
}