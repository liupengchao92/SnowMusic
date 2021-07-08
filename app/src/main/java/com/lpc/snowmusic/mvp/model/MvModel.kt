package com.lpc.snowmusic.mvp.model

import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.bean.MvInfoDetailInfo
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/7/7
 * ClassName :MvModel
 * Desc:
 */
class MvModel : BaseModel() {

    /**
     * 加载mv详情
     */
    fun loadMvDetail(mvid: String?): Observable<MvInfoDetailInfo> =
        RetrofitHelper.nesteaseService.getMvDetailInfo(mvid);


    /**
     * 加载相关的MV列表
     * */
    fun loadSimilarMv(mvid: String?): Observable<*> =
        RetrofitHelper.nesteaseService.getMvDetailInfo(mvid)

    /**
     * 加载Mv的评论
     * */
    fun loadMvComment(id: String?): Observable<*> = RetrofitHelper.nesteaseService.getSimilarMv(id);
}