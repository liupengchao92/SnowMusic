package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.http.bean.BannerBean
import com.lpc.snowmusic.http.bean.BannerResult
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2020/9/27
 * ClassName :DiscoverContract
 * Desc:
 */
interface DiscoverContract {

    interface View : IView {

        fun showBannerView(banners: MutableList<BannerBean>)

        fun showHotSinger()
    }

    interface Presenter : IPresenter<View> {

        fun loadBannerView()

        fun loadBaidu()

        fun loadNetease(tag: String)

        fun loadArtists()

        fun loadRaios()
    }

    interface Model : IModel {

        fun loadBanner(): Observable<BannerResult>

        fun loadBaidu(): Observable<BannerResult>
    }
}