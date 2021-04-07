package com.lpc.snowmusic.ui.activity

import androidx.appcompat.app.ActionBarDrawerToggle
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import com.lpc.snowmusic.ui.adapter.MainFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_container_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initToolBar() {
        toolbar.run {
            title = getString(R.string.snow_music)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun initView() {

        viewpager.run {
            offscreenPageLimit = 3
            adapter = MainFragmentPagerAdapter(this@MainActivity, supportFragmentManager)
            tab_layout.setupWithViewPager(this)
        }

        //ToolBar与DrawerLayout关联
        drawer_layout.run {

            val toggle = ActionBarDrawerToggle(
                this@MainActivity, this, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

            toggle.syncState()
        }

    }
}
