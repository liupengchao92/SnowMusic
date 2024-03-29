package com.lpc.snowmusic.ui.discover.activity

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.SongListDetailContract
import com.lpc.snowmusic.mvp.presenter.SongListDetailPresenter
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.ui.discover.adapter.SongAdapter
import com.lpc.snowmusic.ui.discover.listener.AppBarStateChangeListener
import com.lpc.snowmusic.utils.FormatUtil
import com.lpc.snowmusic.utils.NavigationHelper
import kotlinx.android.synthetic.main.activity_song_list_detail.*

/**
 * 歌单详情
 * */
class SongListDetailActivity :
    BaseMvpActivity<SongListDetailContract.View, SongListDetailContract.Presenter>(),
    SongListDetailContract.View {

    private var playList: Playlist? = null

    private val musicList = mutableListOf<Music>()

    //歌曲适配器
    private val songAdapter: SongAdapter by lazy {
        SongAdapter(musicList)
    }

    override fun getLayoutResId(): Int = R.layout.activity_song_list_detail

    override fun createPresenter(): SongListDetailContract.Presenter = SongListDetailPresenter()

    override fun isShowMediaControl(): Boolean = true


    override fun initView() {
        super.initView()

        playList = intent.getParcelableExtra(Extras.SONG_LIST)

        playList?.let {
            GlideUtils.loadBigImageView(this, it.coverUrl, it.type, iv_background, true)
        }
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@SongListDetailActivity)
            adapter = songAdapter
            itemAnimator = DefaultItemAnimator()
        }
        //
        songAdapter.setOnItemClickListener { adapter, view, position ->
            PlayManager.play(adapter.data as List<Music>, position, playList?.pid!!)
            NavigationHelper.navigateToPlaying(this)
        }

        //AppBarLayout的监听
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {

            override fun onStateChanged(
                appBarLayout: AppBarLayout,
                state: State,
                verticalOffset: Int
            ) {
                when (state) {
                    State.COLLAPSED -> {
                        toolBar.title = playList?.name
                    }
                    State.EXPANDED -> {
                        toolBar.title = resources.getString(R.string.song_list)
                    }
                    else -> {
                        toolBar.title = resources.getString(R.string.song_list)
                    }
                }
            }
        })
    }

    override fun start() {
        super.start()
        presenter?.getSongListDetail(playList?.pid!!)
    }

    override fun showSongListDetail(playlist: Playlist) {
        playlist?.let {
            this.playList = playList
            //歌单封面
            GlideUtils.loadImageView(this, it.coverUrl, iv_cover)
            //歌单标题
            tv_title.text = it.name
            //歌单描述
            tv_description.text = it.des
            //播放次数
            tv_play_count.text = FormatUtil.formatPlayCount(it.playCount.toInt())
        }
    }

    override fun showSongList(mutableList: MutableList<Music>) {
        songAdapter.run {
            setNewInstance(mutableList)
            notifyDataSetChanged()
        }
    }
}
