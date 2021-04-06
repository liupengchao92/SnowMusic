package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
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

        /*model?.loadBanner()?.request(model, view as IView) {
            LogUtils.d("测试：${it.data}")
        }*/
        val observable = model?.loadBanner()
        observable?.request(model, view as IView) {
            view?.showBannerView(it.banners)
        }
    }


    override fun loadBaidu() {
        RetrofitHelper.service
    }

    override fun loadNetease(tag: String) {

    }

    override fun loadArtists() {
    }

    override fun loadRaios() {

    }

}