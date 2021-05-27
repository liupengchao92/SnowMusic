package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView

/**
 * Author: liupengchao
 * Date: 2021/5/27
 * ClassName :PlayLocalContract
 * Desc:
 */
interface PlayLocalContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model : IModel
}