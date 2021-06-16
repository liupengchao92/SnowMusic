package com.lpc.snowmusic.database.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Query("SELECT * FROM LocalAlbum WHERE albumId=:albumId")
    fun queryAlbum(albumId: String?):Cursor

    @Query("SELECT * FROM LocalArtist WHERE artistId=:artistId")
    fun queryArtist(artistId: String?):Cursor

    @Query("SELECT * FROM LocalAlbum")
    fun queryAllAlbum(): List<LocalAlbum>

    @Query("SELECT * FROM LocalArtist")
    fun queryAllArtist(): List<LocalArtist>

}