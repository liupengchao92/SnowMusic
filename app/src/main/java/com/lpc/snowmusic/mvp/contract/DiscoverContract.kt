package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.http.bean.BannerBean

/**
 * Author: liupengchao
 * Date: 2020/9/27
 * ClassName :DiscoverContract
 * Desc:
 */
interface DiscoverContract {

    interface View : IView {

        fun showBannerView(banners: MutableList<BannerBean>)

        fun showHotSinger();
    }

    interface Prensenter : IPresenter<View> {

    }
}