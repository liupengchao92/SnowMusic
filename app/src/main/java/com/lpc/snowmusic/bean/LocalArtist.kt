package com.lpc.snowmusic.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/6/9
 * ClassName :LocalArtist
 * Desc:
 */
@Entity
class LocalArtist() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String? = null
    var artistId: String? = null
    var count: Int = 0
    var type: String? = Constants.LOCAL
    var picUrl: String? = null
    var desc: String? = null
    var musicSize: Int = 0
    var score: Int = 0
    var albumSize: Int = 0

    constructor(artistId: String?, name: String?) : this() {
        this.name = name
        this.artistId = artistId
    }

}