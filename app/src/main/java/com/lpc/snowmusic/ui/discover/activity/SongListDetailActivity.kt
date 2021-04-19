package com.lpc.snowmusic.ui.discover.activity

import android.view.MenuItem
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.imageload.GlideUtils
import kotlinx.android.synthetic.main.activity_song_list_detail.*

/**
 * 歌单详情
 * */
class SongListDetailActivity : BaseActivity() {

    private var playList: Playlist? = null

    override fun getLayoutResId(): Int = R.layout.activity_song_list_detail

    override fun initToolBar() {
        super.initToolBar()

        toolbar.run {
            title = "歌单详情"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun initView() {
        playList = intent.getParcelableExtra(Extras.SONG_LIST)

        playList?.let {
            toolbar.title = it.name
            GlideUtils.loadBigImageView(this, it.coverUrl, it.type, iv_cover)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
