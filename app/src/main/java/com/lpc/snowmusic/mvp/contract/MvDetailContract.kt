package com.lpc.snowmusic.mvp.contract

import com.cyl.musicapi.netease.CommentsItemInfo
import com.cyl.musicapi.netease.MvInfoDetail
import com.lpc.snowmusic.base.IPresenter
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.bean.MvInfoDetailInfo

/**
 * Author: liupengchao
 * Date: 2021/7/7
 * ClassName :MvDetailContract
 * Desc:
 */
interface MvDetailContract {

    interface View : IView {

        fun showMvDetailInfo(mvInfoDetailInfo: MvInfoDetailInfo)

        fun showMvList(mvList: List<MvInfoDetail?>?)

        fun showMvComment(mvCommentInfo: List<CommentsItemInfo?>?)
    }

    interface Presenter : IPresenter<View> {

        fun loadMvDetail(mvId: String?)

        fun loadSimilarMv(mvId: String?)

        fun loadMvComment(mvId: String?)
    }

}