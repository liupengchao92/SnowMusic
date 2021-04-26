package com.lpc.snowmusic.ui.discover

import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.discover.adapter.PlayerAdapter
import com.lpc.snowmusic.ui.discover.fragment.CoverFragment
import com.lpc.snowmusic.ui.discover.fragment.LyricFragment
import kotlinx.android.synthetic.main.activity_player.*

/**
 * 播放详情页面
 * */
class PlayerActivity : BaseActivity() {
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
}
