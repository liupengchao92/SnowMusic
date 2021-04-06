package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.ArtistInfo
import com.lpc.snowmusic.bean.ArtistsInfo
import com.lpc.snowmusic.bean.BannerBean
import com.lpc.snowmusic.bean.BannerResult
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

        fun showHotSinger(artists: MutableList<ArtistInfo>)
    }

    interface Presenter : IPresenter<View> {

        fun loadBannerView()

        fun getHotSinger(offset: Int, limit: Int)
    }

    interface Model : IModel {

        fun loadBanner(): Observable<BannerResult>

        fun getHotSinger(offset: Int, limit: Int): Observable<ArtistsInfo>
    }
}