package com.lpc.snowmusic.database.dao

import androidx.room.*
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.MusicToPlayList

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :MusicDao
 * Desc:音乐操作DAO
 */
@Dao
interface MusicDao {

    //插入播放的音乐
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(music: Music)

    //像音乐插入对应的歌单
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetMusicToPlayList(musicToPlayList: MusicToPlayList)

    //查询对应的歌曲
    @Query("SELECT *FROM Music WHERE mid=:mid")
    fun queryMusic(mid: String): Music

    //获取对应歌单
    @Query("SELECT * FROM MusicToPlayList WHERE pid =:pid")
    fun queryPlayList(pid: String): List<MusicToPlayList>

    //获取对应歌单
    @Query("SELECT * FROM MusicToPlayList WHERE pid =:pid ORDER BY updateDate DESC")
    fun queryPlayListOrder(pid: String): List<MusicToPlayList>

    //查询歌曲是否存在歌单中
    @Query("SELECT * FROM MusicToPlayList WHERE pid =:pid  AND mid=:mid")
    fun queryPlayListMusic(pid: String, mid: String?): MusicToPlayList?

    //查询歌曲是否存在歌单中
    @Update
    fun updatePlayList(musicToPlayList: MusicToPlayList)

    //删除首歌曲
    @Query("DELETE FROM MusicToPlayList WHERE pid =:pid AND mid=:mid")
    fun delete(pid: String?, mid: String?): Int

    //清空歌单
    @Query("DELETE FROM MusicToPlayList WHERE pid =:pid ")
    fun deleteAll(pid: String): Int


}