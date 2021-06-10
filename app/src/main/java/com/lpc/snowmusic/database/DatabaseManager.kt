package com.lpc.snowmusic.database

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.bean.LocalArtist
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.MusicToPlayList

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
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            LogUtils.d("数据库结构没有变化")
        }
    }

    /**
     * 添加到歌曲
     * */
    fun insertMusicDB(music: Music) {
        music.mid?.let {
            //查询是否存在
            val result: Music = database.musicDao().queryMusic(it)
            //不存在插入
            if (result == null) {
                database.musicDao().insertMusic(music)
            }
        }
    }

    /**
     * 添加到歌单
     * */
    fun addToPlaylist(music: Music, pid: String) {
        //插入歌曲表
        insertMusicDB(music)
        //获取歌单中的歌曲
        var mtp = database.musicDao().queryPlayListMusic(pid, music.mid)
        //不存在插入歌单表
        if (mtp == null) {
            mtp = MusicToPlayList()
            mtp.mid = music.mid
            mtp.pid = pid
            mtp.total += 1
            mtp.createDate = System.currentTimeMillis()
            mtp.updateDate = System.currentTimeMillis()
            database.musicDao().insetMusicToPlayList(mtp)
        } else {
            //存着更新时间
            mtp.updateDate = System.currentTimeMillis()
            database.musicDao().updatePlayList(mtp)
        }
    }

    /**
     * 获取播放队列
     *
     * */
    fun getPlayList(pid: String, order: Boolean = false): MutableList<Music> {
        val musicLists = mutableListOf<Music>()
        val musicToPlayList =
            if (order) database.musicDao().queryPlayListOrder(pid)
            else
                database.musicDao().queryPlayList(pid)
        musicToPlayList.forEach {
            val music: Music = database.musicDao().queryMusic(it.mid!!)
            musicLists.add(music)
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


    fun insertAlbum(album: LocalAlbum) {
        database.localDao().insertAlbum(album)
    }

    fun insertArtist(artist: LocalArtist) {
        database.localDao().insertArtist(artist)
    }

    /**
     * 获取本地所有专辑
     * */
    fun getLocalAlbum(): MutableList<LocalAlbum> {
        val localAlbums = mutableListOf<LocalAlbum>()
        val datas = database.localDao().queryAlbum()
        datas.forEach {
            localAlbums.add(it)
        }
        return localAlbums
    }

    /**
     * 获取本地所有歌手
     * */
    fun getLocalArtist(): MutableList<LocalArtist> {
        val artist = mutableListOf<LocalArtist>()
        val datas = database.localDao().queryArtist()
        datas.forEach {
            artist.add(it)
        }
        return artist
    }
}