package com.lpc.snowmusic.database

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.lpc.snowmusic.database.dao.MusicDao

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :MusicDatabaseIml
 * Desc:
 */
class MusicDatabaseIml:MusicDatabase() {

    override fun musicDao(): MusicDao {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}