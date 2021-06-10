package com.lpc.snowmusic.bean

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/6/9
 * ClassName :Album
 * Desc:本地专辑
 */
@Entity
class LocalAlbum() {
    @PrimaryKey
    var id: Int = 0
    var albumId: String? = null
    var name: String? = null
    var artistName: String? = null
    var cover: String? = null
    var type: String? = Constants.LOCAL
    var artistId: String? = null
    var info: String? = null
    var count: Int = 0

    constructor(id: String, name: String, artistName: String, artistId: Long, count: Int):this() {
        this.name = name
        this.albumId = id
        this.artistName = artistName
        this.artistId = artistId.toString()
        this.count = count
    }
}