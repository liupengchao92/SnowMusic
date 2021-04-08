package com.lpc.snowmusic.mvp.model

import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.bean.MvInfo
import com.lpc.snowmusic.bean.PersonalizedInfo
import com.lpc.snowmusic.bean.SearchInfo
import com.lpc.snowmusic.constant.HttpConstant
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
import com.lpc.snowmusic.mvp.contract.MvListContract
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :MvListModel
 * Desc:
 */
class MvListModel : BaseModel(), MvListContract.Model {

    override fun loadPersonalizedMv(): Observable<PersonalizedInfo> = RetrofitHelper.nesteaseService.personalizedMv()

    override fun loadMv(offset: Int, limit: Int): Observable<MvInfo> =
        RetrofitHelper.nesteaseService.getTopMv(offset, limit)

    override fun loadRecentMv(limit: Int): Observable<MvInfo> = RetrofitHelper.nesteaseService.getRecentlyMv(limit)

    override fun searchMv(keywords: String, limit: Int, offset: Int, type: Int): Observable<SearchInfo> {
        val url = HttpConstant.BASE_NETEASE_URL + "/search?keywords= $keywords&limit=$limit&offset=$offset&type=$type"
        return RetrofitHelper.nesteaseService.searchNetease(url)
    }
}