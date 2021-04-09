package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistDetailContract
 * Desc:
 */
interface ArtistDetailContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel {

    }
}