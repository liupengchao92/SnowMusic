package com.lpc.snowmusic.mvp.presenter

import com.lpc.snowmusic.base.BasePresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.MvInfoDetail
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.mvp.contract.MvListContract
import com.lpc.snowmusic.mvp.model.MvListModel

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :MvPresenter
 * Desc:
 */
class MvListPresenter : BasePresenter<MvListContract.View, MvListContract.Model>(), MvListContract.Presenter {


    override fun createModel(): MvListContract.Model? = MvListModel()


    override fun loadPersonalizedMv() {
        model?.loadPersonalizedMv()?.request(model, view as IView) {
            val list = mutableListOf<MvInfoDetail>()
            it.result?.forEach {
                val data = MvInfoDetail(
                    artistId = it.artistId,
                    id = it.id.toInt(),
                    artistName = it.artistName,
                    artists = it.artists,
                    cover = it.picUrl,
                    playCount = it.playCount.toInt(),
                    duration = it.duration,
                    desc = it.copywriter,
                    name = it.name
                )
                list.add(data)
            }
            view?.showMvList(list)
        }
    }

    override fun loadMv(offset: Int, limit: Int) {

        model?.loadMv(offset, limit)?.request(model, view as IView) {
            view?.showMvList(it.data!!)
        }
    }

    override fun loadRecentMv(limit: Int) {
        model?.loadRecentMv(limit)?.request(model, view as IView) {
            view?.showMvList(it.data!!)
        }
    }
}