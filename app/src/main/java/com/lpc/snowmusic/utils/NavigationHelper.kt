package com.lpc.snowmusic.utils

import android.content.Context
import android.content.Intent
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.ui.discover.activity.ArtistDetailActivity

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :NavigationHelper
 * Desc:
 */
object NavigationHelper {

    /**
     *跳转歌手详情
     * */
    fun navigateToArtistDetail(context: Context, artist: Artist) {
        val intent = Intent(context, ArtistDetailActivity::class.java)
        intent.putExtra(Extras.ARTIST, artist)
        context.startActivity(intent)
    }
}