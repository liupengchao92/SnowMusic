package com.lpc.snowmusic.ui.my.activity

import android.content.Context
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.mvp.contract.PlayLocalContract
import com.lpc.snowmusic.mvp.presenter.PlayLocalPresenter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

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
        val navigator: CommonNavigator = CommonNavigator(this)
        navigator.adapter=object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                TODO("Not yet implemented")
            }

            override fun getTitleView(p0: Context?, p1: Int): IPagerTitleView {
                TODO("Not yet implemented")
            }

            override fun getIndicator(p0: Context?): IPagerIndicator {
                TODO("Not yet implemented")
            }
        }
    }

}