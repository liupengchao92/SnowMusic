package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.*
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

        fun showHotSinger(artists: MutableList<Artist>)

        fun showRecommendList(result: MutableList<Playlist>)
    }

    interface Presenter : IPresenter<View> {

        fun loadBannerView()

        fun getHotSinger(offset: Int, limit: Int)

        fun getRecommend()
    }

    interface Model : IModel {

        fun loadBanner(): Observable<BannerResult>

        fun getHotSinger(offset: Int, limit: Int): Observable<ArtistsInfo>

        fun getRecommend(): Observable<PersonalizedInfo>
    }
}