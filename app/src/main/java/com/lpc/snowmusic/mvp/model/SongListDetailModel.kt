package com.lpc.snowmusic.mvp.model

import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.bean.PlaylistDetail
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
import com.lpc.snowmusic.mvp.contract.SongListDetailContract
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/19
 * ClassName :SongListDetailModel
 * Desc:
 */
class SongListDetailModel : BaseModel(), SongListDetailContract.Model {

    override fun getSongListDetail(id: String): Observable<PlaylistDetail> {

        return RetrofitHelper.nesteaseService.getPlaylistDetail(id)
    }
}