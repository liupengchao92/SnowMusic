package com.lpc.snowmusic.database

import androidx.room.Room
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.MusicToPlayList
import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :DatabaseManager
 * Desc:数据库管理类
 */

object DatabaseManager {

    //创建数据库实例
    private val database by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Room.databaseBuilder(MusicApplication.context, MusicDatabase::class.java, "snow_music.db")
            .allowMainThreadQueries()
            .build()

    }

    /**
     * 添加到歌单
     * */
    fun addToPlaylist(music: Music, pid: String) {
        //插入歌曲表
        database.musicDao().insertMusic(music)
        //获取歌单中的歌曲
        var mtp = database.musicDao().queryPlayListMusic(pid, music.mid)
        if (mtp == null) {
            //插入歌单表
            mtp = MusicToPlayList()
            mtp.mid = music.mid
            mtp.pid = pid
            mtp.total += 1
            mtp.createDate = System.currentTimeMillis()
            mtp.updateDate = System.currentTimeMillis()
            database.musicDao().insetMusicToPlayList(mtp)
        } else {
            mtp.updateDate = System.currentTimeMillis()
            database.musicDao().updatePlayList(mtp)
        }
    }

    /**
     * 获取播放队列
     *
     * */
    fun getPlayList(pid: String): MutableList<Music> {
        val musicLists = mutableListOf<Music>()
        when (pid) {
            Constants.PLAYLIST_LOVE_ID -> {

            }
            Constants.PLAYLIST_LOCAL_ID -> {

            }
            else -> {
                //获取歌单
                val musicToPlayList = database.musicDao().queryPlayList(pid)
                musicToPlayList.forEach {
                    val music: Music = database.musicDao().queryMusic(it.mid!!)
                    musicLists.add(music)
                }
            }
        }
        return musicLists
    }

    /**
     *清空歌单
     *
     * */
    fun clearPlaylist(pid: String) {
        //清空数据
        val count = database.musicDao().deleteAll(pid)
        LogUtils.d("clearPlaylist pid:$pid   count:$count")
    }
}