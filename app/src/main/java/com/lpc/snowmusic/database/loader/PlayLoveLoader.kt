package com.lpc.snowmusic.database.loader

import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.database.DatabaseManager

/**
 * Author: liupengchao
 * Date: 2021/6/30
 * ClassName :PlayLoveLoader
 * Desc:
 */
object PlayLoveLoader {

    /**
     * 是否收藏
     * */
    fun isLoveMusic(music: Music): Boolean {
        return DatabaseManager.getMusicFromPlayList(music.mid!!, Constants.PLAYLIST_LOVE_ID) != null
    }

    fun addLoveList(music: Music) {
        DatabaseManager.addToPlaylist(music, Constants.PLAYLIST_LOVE_ID)
    }

    fun removeLove(music: Music) {
        DatabaseManager.deletePlayList(music, Constants.PLAYLIST_LOVE_ID)
    }

    /**
     * 获取播放历史
     * */
    fun getLoveList(): MutableList<Music> {
        return DatabaseManager.getPlayList(Constants.PLAYLIST_LOVE_ID, true)
    }

}