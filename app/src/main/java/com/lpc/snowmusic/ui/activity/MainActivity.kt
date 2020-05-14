package com.lpc.snowmusic.ui.activity

import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
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

        tab_layout.run {


        }

        drawer_layout.run {

        }

    }
}
