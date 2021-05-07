package com.lpc.snowmusic.ui.discover

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.mvp.presenter.PlayPresenter
import com.lpc.snowmusic.ui.discover.adapter.PlayerAdapter
import com.lpc.snowmusic.ui.discover.fragment.CoverFragment
import com.lpc.snowmusic.ui.discover.fragment.LyricFragment
import kotlinx.android.synthetic.main.activity_player.*

/**
 * 播放详情页面
 * */
class PlayerActivity : BaseMvpActivity<PlayContract.View, PlayContract.Presenter>(), PlayContract.View {
    //封面Fragment
    private var coverFragment: CoverFragment? = null
    //歌词Fragment
    private var lyricFragment: LyricFragment? = null
    //适配器
    private val playerAdapter by lazy { PlayerAdapter(this) }

    override fun getLayoutResId(): Int = R.layout.activity_player

    override fun initToolBar() {
        //返回页面
        backIv.setOnClickListener { finish() }
        //歌曲操作
        operateSongIv.setOnClickListener { ToastUtils.showShort("") }
    }

    override fun initView() {
        super.initView()
        //添加Fragment
        coverFragment = CoverFragment.getInstance()
        lyricFragment = LyricFragment.getInstance()
        playerAdapter.addFragment(coverFragment as BaseFragment)
        playerAdapter.addFragment(lyricFragment as BaseFragment)
        //设置ViewPager
        viewPager2.run {
            adapter = playerAdapter
        }
    }

    override fun start() {
        super.start()
        presenter?.getPlayingMusic()
    }

    override fun createPresenter(): PlayContract.Presenter = PlayPresenter()

    override fun showPlayingMusic(music: Music) {
        LogUtils.e("测试：   showPlayingMusic")

        LogUtils.e("歌曲信息：$music")
        //歌曲标题
        songNameTv.text = music.title
        //歌手
        singerName.text = music.artist
        //背景
        GlideUtils.loadBigImageView(this, music.coverUri, music.type, backgroundIv, true, 30)

    }

    override fun showLyric(lyric: String?, init: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProgress(progress: Long, max: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
