package com.lpc.snowmusic.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: liupengchao
 * Date: 2021/5/21
 * ClassName :MusicToPlaylist
 * Desc:歌曲列表
 */
@Entity
class MusicToPlayList {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var pid: String? = null
    var mid: String? = null
    var total: Long = 0
    var updateDate: Long = 0
    var createDate: Long = 0
}