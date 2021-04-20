package com.lpc.snowmusic.ui.discover.activity

import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity

/**
 * 歌单广场
 * */
class SongListSquareActivity : BaseActivity() {


    override fun getLayoutResId(): Int = R.layout.activity_song_list_square

    override fun initImmersionBar() {

    }

    override fun initToolBar() {
        super.initToolBar()
        toolBar.title = resources.getString(R.string.song_list_square)
    }

    override fun initView() {

    }
}
