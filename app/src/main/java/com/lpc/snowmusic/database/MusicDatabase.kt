package com.lpc.snowmusic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lpc.snowmusic.bean.*
import com.lpc.snowmusic.database.dao.LocalDao
import com.lpc.snowmusic.database.dao.MusicDao

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :MusicDataBase
 * Desc:数据库Database
 */
@Database(
    entities = [Music::class, MusicToPlayList::class,
        LocalAlbum::class, LocalArtist::class], version = 1
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    abstract fun localDao(): LocalDao

}