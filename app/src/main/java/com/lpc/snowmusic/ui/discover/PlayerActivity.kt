package com.lpc.snowmusic.ui.discover

import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*

/**
 * 播放详情页面
 * */
class PlayerActivity : BaseActivity() {


    override fun getLayoutResId(): Int = R.layout.activity_player

    override fun initToolBar() {
        //返回页面
        backIv.setOnClickListener { finish() }
        //歌曲操作
        operateSongIv.setOnClickListener { ToastUtils.showShort("") }
    }

    override fun initView() {

    }
}
