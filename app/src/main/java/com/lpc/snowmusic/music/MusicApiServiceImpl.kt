package com.lpc.snowmusic.music

import com.blankj.utilcode.util.LogUtils
import com.cyl.musicapi.BaseApiImpl
import com.lpc.snowmusic.constant.Constants
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/5/6
 * ClassName :MusicApiServiceImpl
 * Desc:
 */
object MusicApiServiceImpl {


    /**
     * 获取歌曲url信息
     * @param br 音乐品质
     *
     */
    fun getMusicUrl(vendor: String, mid: String, br: Int = 128000): Observable<String> {
        LogUtils.d("getMusicUrl $vendor $mid $br")
        return Observable.create { result ->
            BaseApiImpl.getSongUrl(vendor, mid, br, {
                if (it.status) {
                    val url =
                        if (vendor == Constants.XIAMI) {
                            if (it.data.url.startsWith("http")) it.data.url
                            else "http:${it.data.url}"
                        } else it.data.url
                    result.onNext(url)
                    result.onComplete()
                } else {
                    result.onError(Throwable(it.msg))
                }
            }, {})
        }
    }
}