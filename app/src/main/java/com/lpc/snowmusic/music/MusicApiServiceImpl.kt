package com.lpc.snowmusic.music

import com.cyl.musicapi.BaseApiImpl
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.utils.FileUtils
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

    /**
     * 获取本地歌词
     */
    fun getLocalLyricInfo(music: Music): Observable<String> {
        val mLyricPath = FileUtils.getLrcDir() + music.title + "-" + music.artist + ".lrc"
        //网络歌词
        return MusicApi.getLocalLyricInfo(mLyricPath)
    }


    /**
     * 保存歌词
     */
    fun saveLyricInfo(name: String, artist: String, lyricInfo: String) {
        val mLyricPath = FileUtils.getLrcDir() + "$name-$artist.lrc"
        val save = FileUtils.writeText(mLyricPath, lyricInfo)
    }

    /**
     * 获取歌词
     *
     */
    fun getLyricInfo(music: Music): Observable<String>? {
        try {
            val mLyricPath = FileUtils.getLrcDir() + "${music.title}-${music.artist}" + ".lrc"
            val vendor = music.type!!
            val mid = music.mid!!
            //网络歌词
            return if (FileUtils.exists(mLyricPath)) {
                MusicApi.getLocalLyricInfo(mLyricPath)
            } else Observable.create { result ->
                BaseApiImpl.getLyricInfo(vendor, mid) {
                    if (it.status) {
                        val lyricInfo = it.data.lyric
                        val lyric = StringBuilder()
                        lyricInfo.forEach {
                            lyric.append(it)
                            lyric.append("\n")
                        }
                        it.data.translate.forEach {
                            lyric.append(it)
                            lyric.append("\n")
                        }
                        //保存文件
                        val save = FileUtils.writeText(mLyricPath, lyric.toString())
                        Observable.fromArray(lyric)
                        result.onNext(lyric.toString())
                        result.onComplete()
                    } else {
                        result.onError(Throwable(it.msg))
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }
}