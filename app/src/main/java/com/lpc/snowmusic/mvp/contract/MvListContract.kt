package com.lpc.snowmusic.mvp.contract

import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.MvInfo
import com.lpc.snowmusic.bean.MvInfoDetail
import com.lpc.snowmusic.bean.PersonalizedInfo
import com.lpc.snowmusic.bean.SearchInfo
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :MvListContract
 * Desc:MV列表
 */
interface MvListContract {

    interface View : IView {

        fun showMvList(mvList: MutableList<MvInfoDetail>)
    }

    interface Presenter : IPresenter<View> {

        fun loadPersonalizedMv()

        fun loadMv(offset: Int, limit: Int)

        fun loadRecentMv(limit: Int)

        fun searchMv(key: String, offset: Int)
    }

    interface Model : IModel {

        fun loadPersonalizedMv(): Observable<PersonalizedInfo>

        fun loadMv(offset: Int, limit: Int): Observable<MvInfo>

        fun loadRecentMv(limit: Int): Observable<MvInfo>

        fun searchMv(keywords: String, limit: Int, offset: Int, type: Int): Observable<SearchInfo>
    }
}