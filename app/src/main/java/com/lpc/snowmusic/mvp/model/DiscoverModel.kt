package com.lpc.snowmusic.mvp.model

import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.http.bean.BannerBean
import com.lpc.snowmusic.http.bean.HttpResult
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/1
 * ClassName :DiscoverModel
 * Desc:
 */
class DiscoverModel : BaseModel(), DiscoverContract.Model {
    override fun loadBanner(): Observable<HttpResult<MutableList<BannerBean>>> {
        return RetrofitHelper.nesteaseService.getBanner()
    }

    override fun loadBaidu(): Observable<HttpResult<MutableList<BannerBean>>> {
        return RetrofitHelper.nesteaseService.getBanner()
    }
}