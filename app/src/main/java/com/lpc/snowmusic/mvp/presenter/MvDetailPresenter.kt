package com.lpc.snowmusic.mvp.presenter

import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.bean.MvInfoDetailInfo
import com.lpc.snowmusic.http.function.RequestCallBack
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.mvp.contract.MvDetailContract
import com.lpc.snowmusic.mvp.model.MvModel

/**
 * Author: liupengchao
 * Date: 2021/7/7
 * ClassName :MvDetailPresenter
 * Desc:
 */
class MvDetailPresenter : BasePresenter<MvDetailContract.View, MvModel>(),
    MvDetailContract.Presenter {

    override fun createModel(): MvModel? = MvModel()

    override fun loadMvDetail(mvId: String?) {

        model?.let {
            it.loadMvDetail(mvId)?.request(object : RequestCallBack<MvInfoDetailInfo> {

                override fun onSuccess(t: MvInfoDetailInfo) {
                    LogUtils.e("showMvDetailInfo  :$t")

                    view?.showMvDetailInfo(t as MvInfoDetailInfo)
                }

                override fun onError(e: Throwable) {
                }
            })
        }
    }

    override fun loadSimilarMv(mvId: String?) {

    }

    override fun loadMvComment(mvId: String?) {
    }


}