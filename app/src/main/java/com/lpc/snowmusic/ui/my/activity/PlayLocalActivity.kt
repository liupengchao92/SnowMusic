package com.lpc.snowmusic.ui.my.activity

import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.mvp.contract.PlayLocalContract
import com.lpc.snowmusic.mvp.presenter.PlayLocalPresenter

class PlayLocalActivity : BaseMvpActivity<PlayLocalContract.View, PlayLocalContract.Presenter>() {

    override fun getLayoutResId(): Int = R.layout.activity_play_local

    override fun createPresenter(): PlayLocalContract.Presenter = PlayLocalPresenter()

    override fun initImmersionBar() {

    }

    override fun initToolBar() {
        toolBar.title = getString(R.string.item_local)
        super.initToolBar()
    }

    override fun initView() {
        super.initView()
        LogUtils.d("标题：${toolBar.title}")
    }

}