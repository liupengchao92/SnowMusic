package com.lpc.snowmusic.player

import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.database.DatabaseManager
import kotlinx.coroutines.*
import okhttp3.internal.wait
import org.jetbrains.anko.doAsync

/**
 * Author: liupengchao
 * Date: 2021/5/25
 * ClassName :PlayHistoryLoader
 * Desc:播放历史
 */
object PlayHistoryLoader {


    /**
     * 添加播放历史
     * */
    fun addHistoryList(music: Music) {
        doAsync {
            DatabaseManager.addToPlaylist(music, Constants.PLAYLIST_HISTORY_ID)
        }
    }


    /**
     * 获取播放历史
     * */
    fun getHistoryList(): MutableList<Music> {

       /* var historyList = mutableListOf<Music>()
        GlobalScope.launch {
           getHistoryList().wait()
        }*/

        return DatabaseManager.getPlayList(Constants.PLAYLIST_HISTORY_ID)
    }

    /**
     * 清空历史
     * */
    fun clearHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            async {
                DatabaseManager.clearPlaylist(Constants.PLAYLIST_HISTORY_ID)
            }
        }
    }
}