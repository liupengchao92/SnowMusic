package com.lpc.snowmusic.music.baidu

import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.http.retrofit.RetrofitHelper
import com.lpc.snowmusic.utils.FileUtils
import io.reactivex.Observable

/**
 * Author: liupengchao
 * Date: 2021/5/6
 * ClassName :BaiduApiServiceImpl
 * Desc:
 */
object BaiduApiServiceImpl {

    /**
     * 获取歌曲详情
     * "http://music.baidu.com/data/music/links?songIds=$mid"
     */
    fun getTingSongInfo(music: Music): Observable<Music> {
        return Observable.create { e ->
            if (music.uri != null) {
                e.onNext(music)
                e.onComplete()
            } else {
                e.onError(Throwable())
            }
        }
    }

    /**
     * 获取歌词
     */
    fun getBaiduLyric(music: Music): Observable<String>? {
        //本地歌词路径
        val mLyricPath = FileUtils.getLrcDir() + music.title + "-" + music.artist + ".lrc"
        //网络歌词
        val mLyricUrl = music.lyric
        return if (FileUtils.exists(mLyricPath)) {
            Observable.create { emitter ->
                try {
                    val lyric: String? = FileUtils.readFile(mLyricPath)
                    emitter.onNext(lyric!!)
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        } else if (mLyricUrl != null) {
            RetrofitHelper.baiDuService.getBaiduLyric(mLyricUrl)
                .flatMap { baiDuLyricInfo ->
                    val lyric = baiDuLyricInfo.string()
                    //保存文件
                    val save = FileUtils.writeText(mLyricPath, lyric)
                    Observable.fromArray(lyric)
                }
        } else {
            null
        }
    }

}