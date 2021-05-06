package com.lpc.snowmusic.music.baidu

import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.utils.MusicUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Author: liupengchao
 * Date: 2021/5/6
 * ClassName :BaiduApiServiceImpl
 * Desc:
 */
object BaiduApiServiceImpl {

    /**
     * 获取歌曲详情
     * "http://music.baidu.com/data/music/links?songIds=$mid"
     */
    fun getTingSongInfo(music: Music): Observable<Music> {
       /* val url = Constants.URL_GET_SONG_INFO + music.mid
        return apiService.getTingSongInfo(url)
            .flatMap { data ->
                val songInfo = data.data.songList?.get(0)
                songInfo?.let {
                    music.type = Constants.BAIDU
                    music.isOnline = true
                    music.mid = songInfo.songId.toString()
                    music.album = songInfo.albumName
                    music.albumId = songInfo.albumId.toString()
                    music.artistId = songInfo.artistId
                    music.artist = songInfo.artistName
                    music.title = songInfo.songName
                    music.uri = songInfo.songLink
                    music.fileSize = songInfo.size.toLong()
                    music.lyric = songInfo.lrcLink
                    music.coverSmall = MusicUtils.getAlbumPic(songInfo.songPicSmall, Constants.BAIDU, MusicUtils.PIC_SIZE_SMALL)
                    music.coverUri = MusicUtils.getAlbumPic(songInfo.songPicSmall, Constants.BAIDU, MusicUtils.PIC_SIZE_NORMAL)
                    music.coverBig = MusicUtils.getAlbumPic(songInfo.songPicSmall, Constants.BAIDU, MusicUtils.PIC_SIZE_BIG)

                }
                Observable.create(ObservableOnSubscribe<Music> { e ->
                    if (music.uri != null) {
                        SongLoader.updateMusic(music)
                        e.onNext(music)
                        e.onComplete()
                    } else {
                        e.onError(Throwable())
                    }
                })
            }*/

        return  Observable.create(ObservableOnSubscribe<Music> { e ->
            if (music.uri != null) {
               // SongLoader.updateMusic(music)
                e.onNext(music)
                e.onComplete()
            } else {
                e.onError(Throwable())
            }
        })
    }
}