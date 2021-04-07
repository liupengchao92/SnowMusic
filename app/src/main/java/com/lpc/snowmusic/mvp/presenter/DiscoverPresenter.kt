package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
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
        model?.loadBanner()?.request(model, view as IView) {
            view?.showBannerView(it.banners)
        }
    }

    override fun getHotSinger(offset: Int, limit: Int) {
        model?.getHotSinger(offset, limit)?.request(model, view as IView) {
            view?.showHotSinger(it.list.artists!!)
        }
    }

    override fun getRecommend() {
        model?.getRecommend()?.request(model, view as IView) {
            view?.showRecommendList(it.result!!)
        }
    }
}