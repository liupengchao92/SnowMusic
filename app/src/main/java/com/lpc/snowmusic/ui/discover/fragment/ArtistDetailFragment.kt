package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.bean.Artist
import kotlinx.android.synthetic.main.fragment_artist_detail.*

/**
 * Author: liupengchao
 * Date: 2021/4/13
 * ClassName :ArtistDetailFragment
 * Desc:歌手详情描述
 */
class ArtistDetailFragment : BaseFragment() {

    override fun getLayoutResId(): Int = R.layout.fragment_artist_detail

    override fun initView(view: View) {

    }

    /**
     * 设置描述
     * */
    fun updateArtistDesc(artist: Artist) {
        artist?.let {
            //设置描述
            tv_desc.text = artist.desc
        }
    }

    override fun lazyLoad() {
    }
}