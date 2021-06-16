package com.lpc.snowmusic.database.loader

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.bean.LocalArtist
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.database.DatabaseManager

/**
 * Author: liupengchao
 * Date: 2021/5/27
 * ClassName :PlayLocalLoader
 * Desc:本地音乐
 */
object PlayLocalLoader {


    /**
     * 获取本地音乐
     * */
    fun getLocalMusic(context: Context, isReload: Boolean = false): MutableList<Music> {
        var localList = getLocalFromDb()
        if (isReload) {
            localList.clear()
            var musicList = getAllLocalSongs(context)
            localList.addAll(musicList)
            localList.forEach {
                try {
                    //插入专辑
                    DatabaseManager.insertAlbum(
                        LocalAlbum(
                            it.albumId,
                            it.album,
                            it.artist,
                            it.artistId
                        )
                    )
                    //插入歌手
                    DatabaseManager.insertArtist(LocalArtist(it.artistId, it.artist))
                } catch (e: java.lang.Exception) {
                    LogUtils.d("插入失败： ${e.message}")
                    e.printStackTrace()
                }
            }
        }
        return localList
    }

    fun getLocalAlbum(context: Context): MutableList<LocalAlbum> {
        return DatabaseManager.getLocalAlbum()
    }


    fun getLocalArtist(context: Context): MutableList<LocalArtist> {
        return DatabaseManager.getLocalArtist()
    }

    /**
     * 从数据库中查询本地歌曲
     * */
    fun getLocalFromDb(): MutableList<Music> {
        return DatabaseManager.getPlayList(Constants.PLAYLIST_LOCAL_ID)
    }


    fun getAllLocalSongs(context: Context): MutableList<Music> {
        return getSongsForMedia(context, makeSongCursor(context, null, null))
    }


    fun makeSongCursor(
        context: Context,
        selection: String?,
        paramArrayOfString: Array<String>?
    ): Cursor? {
        val songSortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        return makeSongCursor(context, selection, paramArrayOfString, songSortOrder)
    }

    /**
     * 搜素本地音乐
     */
    private fun makeSongCursor(
        context: Context,
        selection: String?,
        paramArrayOfString: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var selectionStatement = "duration>60000 AND is_music=1 AND title != ''"

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = "$selectionStatement AND $selection"
        }
        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                "_id",
                "title",
                "artist",
                "album",
                "duration",
                "track",
                "artist_id",
                "album_id",
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE
            ),
            selectionStatement, paramArrayOfString, sortOrder
        )
    }

    /**
     * 获取本地的歌曲
     * */
    private fun getSongsForMedia(context: Context, cursor: Cursor?): MutableList<Music> {
        //歌曲集合
        val result = mutableListOf<Music>()

        try {

            cursor?.run {
                //将游标移动到第一个，因为得到的Cursor是指向第一条记录之前的
                if (moveToFirst()) {
                    do {
                        //创建歌曲实例
                        val music = Music()
                        //歌曲类型
                        music.type = Constants.LOCAL
                        //是否在线
                        music.isOnline = false
                        //歌曲名称
                        music.title = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                        //id
                        music.mid =
                            getLong(getColumnIndexOrThrow(MediaStore.Audio.Media._ID)).toString()
                        //专辑
                        music.album = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                        //专辑标识
                        music.albumId =
                            getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                        //歌手
                        music.artist =
                            getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                        //歌手标识
                        music.artistId =
                            getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID))
                        //路径
                        music.uri = getString(getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                        //图片 封面
                        music.coverUri = getCoverUri(
                            context,
                            getString(getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                        )
                        //
                        music.trackNumber =
                            getInt(getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK))
                        //歌曲大小
                        music.duration =
                            getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                        //时间
                        music.date = System.currentTimeMillis()
                        //
                        result.add(music)
                        //存入歌单数据库
                        DatabaseManager.addToPlaylist(music, Constants.PLAYLIST_LOCAL_ID)

                    } while (moveToNext())

                    //关闭游标
                    close()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e("扫描本地音乐失败 ：${e.message}")
        }

        return result;
    }


    fun getCoverUri(context: Context, albumId: String): String? {
        if (albumId == "-1") {
            return null
        }
        var uri: String? = null
        try {
            val cursor = context.contentResolver.query(
                Uri.parse("content://media/external/audio/albums/$albumId"),
                arrayOf("album_art"), null, null, null
            )
            if (cursor != null) {
                cursor.moveToNext()
                uri = cursor.getString(0)
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri
    }

}