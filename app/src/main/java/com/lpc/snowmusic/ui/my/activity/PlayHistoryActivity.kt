package com.lpc.snowmusic.ui.my.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.ui.discover.adapter.SongAdapter
import com.lpc.snowmusic.utils.NavigationHelper
import kotlinx.android.synthetic.main.activity_play_history.*

class PlayHistoryActivity : BaseActivity() {


    private var songAdapter: SongAdapter? = null

    override fun getLayoutResId(): Int = R.layout.activity_play_history

    override fun initImmersionBar() {

    }

    override fun initToolBar() {
        toolBar.title = getString(R.string.item_history)
        super.initToolBar()
    }

    override fun initView() {

        var playList = intent.getSerializableExtra(Extras.PLAY_LIST) as MutableList<Music>
        if (playList.isEmpty()) {
            multiple_StatusView.showEmpty()
        } else {
            songAdapter = SongAdapter(playList)
            songAdapter?.setOnItemClickListener { baseQuickAdapter, view, i ->
                PlayManager.play(playList, i, Constants.PLAYLIST_HISTORY_ID)
                songAdapter?.notifyDataSetChanged()
                NavigationHelper.navigateToPlaying(this);
            }
            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                adapter = songAdapter
            }
        }
    }
}
