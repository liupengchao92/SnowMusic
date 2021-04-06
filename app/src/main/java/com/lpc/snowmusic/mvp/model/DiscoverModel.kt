package com.lpc.snowmusic.mvp.model

import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.bean.ArtistsInfo
import com.lpc.snowmusic.bean.BannerResult
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
    override fun getHotSinger(offset: Int, limit: Int): Observable<ArtistsInfo> {
        return RetrofitHelper.nesteaseService.getTopArtists(offset, limit)
    }

    override fun loadBanner(): Observable<BannerResult> {
        return RetrofitHelper.nesteaseService.getBanner()
    }
}