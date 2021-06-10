package com.lpc.snowmusic.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.bean.LocalArtist

/**
 * Author: liupengchao
 * Date: 2021/6/9
 * ClassName :LocalDao
 * Desc:
 */
@Dao
interface LocalDao {

    @Insert
    fun insertAlbum(localAlbum: LocalAlbum)

    @Insert
    fun insertArtist(artist: LocalArtist)

    @Query("SELECT * FROM LocalAlbum")
    fun queryAlbum(): List<LocalAlbum>

    @Query("SELECT * FROM LocalArtist")
    fun queryArtist(): List<LocalArtist>

}