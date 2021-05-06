package com.lpc.snowmusic.music

import com.blankj.utilcode.util.FileUtils
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.music.baidu.BaiduApiServiceImpl
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Author: liupengchao
 * Date: 2021/5/6
 * ClassName :MusicApi
 * Desc:
 */
object MusicApi {

    /**
     * 搜索音乐具体信息（QQ音乐的播放地址会在一定的时间后失效（大概一天））
     *
     */
    fun getMusicInfo(music: Music): Observable<Music> {
        //获取默认音质
        var quality = 128000
        //判断是否当前音质
        if (music.quality != quality) {
            quality = when {
                quality >= 999000 && music.sq -> 999000
                quality >= 320000 && music.hq -> 320000
                quality >= 192000 && music.high -> 192000
                quality >= 128000 -> 128000
                else -> 128000
            }
        }

        val cachePath = ""
        val downloadPath = ""
        if (FileUtils.isFileExists(cachePath)) {
            return Observable.create {
                music.uri = cachePath
                if (music.uri != null) {
                    it.onNext(music)
                    it.onComplete()
                } else {
                    it.onError(Throwable(""))
                }
            }
        } else if (FileUtils.isFileExists(downloadPath)) {
            return Observable.create {
                music.uri = downloadPath
                if (music.uri != null) {
                    it.onNext(music)
                    it.onComplete()
                } else {
                    it.onError(Throwable(""))
                }
            }
        }
        return when (music.type) {
            Constants.BAIDU -> BaiduApiServiceImpl.getTingSongInfo(music).flatMap { result ->
                Observable.create(ObservableOnSubscribe<Music> {
                    music.uri = result.uri
                    music.lyric = result.lyric
                    if (music.uri != null) {
                        it.onNext(music)
                        it.onComplete()
                    } else {
                        it.onError(Throwable(""))
                    }
                })
            }
            else -> {
                MusicApiServiceImpl.getMusicUrl(
                    music.type
                        ?: Constants.LOCAL, music.mid
                        ?: "", quality
                ).flatMap { result ->
                    Observable.create(ObservableOnSubscribe<Music> {
                        music.uri = result
                        if (music.uri != null) {
                            it.onNext(music)
                            it.onComplete()
                        } else {
                            it.onError(Throwable(""))
                        }
                    })
                }
            }
        }
    }
}