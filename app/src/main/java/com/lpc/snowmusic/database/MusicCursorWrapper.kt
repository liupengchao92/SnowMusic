package com.lpc.snowmusic.database

import android.database.Cursor
import android.database.CursorWrapper
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.bean.LocalArtist

/**
 * Author: liupengchao
 * Date: 2021/6/9
 * ClassName :MusicCursorWrapper
 * Desc:
 */
class MusicCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    val album: LocalAlbum
        get() {
            val id = getString(getColumnIndex("albumid"))
            val name = getString(getColumnIndex("album"))
            val artistId = getLong(getColumnIndex("artistid"))
            val artist = getString(getColumnIndex("artist"))
            val num = getInt(getColumnIndex("num"))

            return LocalAlbum(id, name, artist, artistId, num)
        }

    val artist: LocalArtist
        get() {
            val id = getLong(getColumnIndex("artistid"))
            val name = getString(getColumnIndex("artist"))
            val num = getInt(getColumnIndex("num"))
            return LocalArtist(id, name, num)
        }
}