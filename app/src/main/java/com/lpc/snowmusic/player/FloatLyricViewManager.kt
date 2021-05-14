package com.lpc.snowmusic.player

import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.event.LyricUpdateEvent
import com.lpc.snowmusic.http.function.RequestCallBack
import com.lpc.snowmusic.http.function.request
import com.lpc.snowmusic.music.MusicApi
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2021/5/14
 * ClassName :FloatLyricViewManager
 * Desc:歌词管理类
 * */
object FloatLyricViewManager {
    //歌词
    var lyricInfo: String = ""

    /**
     * 加载歌词
     *
     * */
    fun loadLyric(playingMusic: Music?) {
        playingMusic?.let {
            MusicApi.getLyricInfo(it)?.request(object : RequestCallBack<String> {
                override fun onSuccess(t: String) {
                    //更新歌词
                    updateLyric(t)
                    LogUtils.e("${it.title}歌词 : $t")
                }

                override fun onError(e: Throwable) {
                    LogUtils.e("${it.title}加载歌词失败")
                }
            })
        }
    }

    /**
     * 更新歌词
     *
     * @param info 歌词
     */
    private fun updateLyric(info: String) {
        lyricInfo = info
        //发送歌词更新时间
        EventBus.getDefault().post(LyricUpdateEvent(lyricInfo))
    }
}