package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistSongFragment
 * Desc:歌曲
 */
class ArtistSongFragment : BaseFragment() {

    companion object {
        fun getInstance(): BaseFragment {
            val artistSongFragment = ArtistSongFragment()
            return artistSongFragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_artist_song

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}