package com.lpc.snowmusic.player

import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.database.DatabaseManager
import org.jetbrains.anko.doAsync

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :PlayQueueLoader
 * Desc:加载播放队列 */
object PlayQueueLoader {


    /**
     * 获取播放队列
     */
    fun getPlayQueue(): MutableList<Music> {
        return DatabaseManager.getPlayList(Constants.PLAYLIST_QUEUE_ID)
    }

    /**
     * 存储播放队列
     */
    fun saveQueue(musicList: List<Music>) {
        //异步执行
        doAsync {
            //清空播放队列
            clearQueue()
            //添加新的歌曲
            musicList.forEach {
                //加入播放队列
                DatabaseManager.addToPlaylist(it, Constants.PLAYLIST_QUEUE_ID)
            }
        }
    }

    /**
     * 清空队列
     */
    fun clearQueue() {
        DatabaseManager.clearPlaylist(Constants.PLAYLIST_QUEUE_ID)
    }
}