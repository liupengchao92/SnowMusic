package com.lpc.snowmusic.mvp.model

import com.blankj.utilcode.util.LogUtils
import com.cyl.musicapi.BaseApiImpl
import com.lpc.snowmusic.base.BaseModel
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.mvp.contract.ArtistDetailContract
import com.lpc.snowmusic.utils.MusicUtils
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/4/12
 * ClassName :AtistDetailModel
 * Desc:
 */
class ArtistDetailModel : BaseModel(), ArtistDetailContract.Model {
    override fun loadArtistSongs(vendor: String, id: String, offset: Int, limit: Int): Observable<Artist> {
        return Observable.create { result ->
            BaseApiImpl
                .getArtistSongs(vendor, id, offset, limit, {
                    if (it.status) {
                        LogUtils.d("数据", it.toString())
                        val musicList = arrayListOf<Music>()
                        it.data.songs.forEach {
                            if (!it.cp) {
                                it.vendor = vendor
                                musicList.add(MusicUtils.getMusic(it))
                            }
                        }
                        val artist = Artist()
                        artist.songs = musicList
                        artist.name = it.data.detail.name
                        artist.picUrl = it.data.detail.cover
                        artist.desc = it.data.detail.desc
                        artist.artistId = it.data.detail.id
                        result.onNext(artist)
                        result.onComplete()
                    } else {
                        result.onError(Throwable(it.msg))
                    }
                }, {})
        }

    }
}