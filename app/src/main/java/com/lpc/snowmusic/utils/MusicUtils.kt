package com.lpc.snowmusic.utils

import com.cyl.musicapi.netease.TracksItem
import com.cyl.musicapi.playlist.MusicInfo
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :MusicUtils
 * Desc:
 */
object MusicUtils {
    val PIC_SIZE_SMALL = 0
    val PIC_SIZE_NORMAL = 1
    val PIC_SIZE_BIG = 2
    /**
     * 根据不同的歌曲类型生成不同的图片
     * @param size
     */
    /**
     * 根据不同的歌曲类型生成不同的图片
     * @param size
     */
    fun getAlbumPic(url: String?, type: String?, size: Int): String? {
        println(url)
        return when (type) {
            Constants.QQ -> {
                when (size) {
                    PIC_SIZE_SMALL -> {
                        url?.replace("150x150", "90x90")
                    }
                    PIC_SIZE_NORMAL -> {
                        url?.replace("150x150", "150x150")
                    }
                    else -> {
                        url?.replace("150x150", "300x300")
                    }
                }
            }
            Constants.XIAMI -> {
                val tmp = url?.split("@")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$tmp@1e_1c_100Q_90w_90h"
                    PIC_SIZE_NORMAL -> "$tmp@1e_1c_100Q_150w_150h"
                    else -> "$tmp@1e_1c_100Q_450w_450h"
                }
            }
            Constants.NETEASE -> {
                val temp = url?.split("?")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$temp?param=90y90"
                    PIC_SIZE_NORMAL -> "$temp?param=150y150"
                    else -> "$temp?param=450y450"
                }
            }
            Constants.BAIDU -> {
                val tmp = url?.split("@")?.get(0) ?: url
                when (size) {
                    PIC_SIZE_SMALL -> "$tmp@s_1,w_90,h_90"
                    PIC_SIZE_NORMAL -> "$tmp@s_1,w_150,h_150"
                    else -> "$tmp@s_1,w_450,h_450"
                }
            }
            else -> {
                url
            }
        }
    }

    /**
     * 在线歌单歌曲歌曲实体类转化成本地歌曲实体
     */
    fun getMusic(musicInfo: MusicInfo): Music {
        val music = Music()
        if (musicInfo.songId != null) {
            music.mid = musicInfo.songId
        } else if (musicInfo.id != null) {
            music.mid = musicInfo.id
        }
        music.collectId = musicInfo.id
        music.title = musicInfo.name
        music.isOnline = true
        music.type = musicInfo.vendor
        music.album = musicInfo.album.name
        music.albumId = musicInfo.album.id
        music.isCp = musicInfo.cp
        music.isDl = musicInfo.dl
        musicInfo.quality?.let {
            music.sq = it.sq
            music.hq = it.hq
            music.high = it.high
        }
        if (musicInfo.artists != null) {
            var artistIds = musicInfo.artists?.get(0)?.id
            var artistNames = musicInfo.artists?.get(0)?.name
            for (j in 1 until musicInfo.artists?.size!! - 1) {
                artistIds += ",${musicInfo.artists?.get(j)?.id}"
                artistNames += ",${musicInfo.artists?.get(j)?.name}"
            }
            music.artist = artistNames
            music.artistId = artistIds
        }
        music.coverUri = getAlbumPic(musicInfo.album.cover, musicInfo.vendor, PIC_SIZE_NORMAL)
        music.coverBig = getAlbumPic(musicInfo.album.cover, musicInfo.vendor, PIC_SIZE_NORMAL)
        music.coverSmall = getAlbumPic(musicInfo.album.cover, musicInfo.vendor, PIC_SIZE_SMALL)
        return music
    }


    fun getNeteaseMusicList(tracks: MutableList<TracksItem>?): MutableList<Music> {
        val musicList = mutableListOf<Music>()
        tracks?.forEach {
            val music = Music()
            music.mid = it.id.toString()
            music.title = it.name
            music.type = Constants.NETEASE
            music.album = it.al.name
            music.isOnline = true
            music.albumId = it.al.id.toString()
            if (it.ar != null) {
                var artistIds = it.ar?.get(0)?.id.toString()
                var artistNames = it.ar?.get(0)?.name
                for (j in 1 until it.ar?.size!! - 1) {
                    artistIds += ",${it.ar?.get(j)?.id}"
                    artistNames += ",${it.ar?.get(j)?.name}"
                }
                music.artist = artistNames
                music.artistId = artistIds
            }
            music.coverUri = getAlbumPic(it.al.picUrl, Constants.NETEASE, PIC_SIZE_NORMAL)
            music.coverBig = getAlbumPic(it.al.picUrl, Constants.NETEASE, PIC_SIZE_BIG)
            music.coverSmall = getAlbumPic(it.al.picUrl, Constants.NETEASE, PIC_SIZE_SMALL)
//            if (it.cp != 0) {
            musicList.add(music)
//            }
        }
        return musicList
    }
}