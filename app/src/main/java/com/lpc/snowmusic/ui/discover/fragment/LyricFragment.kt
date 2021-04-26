package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :LyricFragment
 * Desc:歌词显示
 * */
class LyricFragment : BaseFragment() {

    companion object {
        fun getInstance(): LyricFragment {
            val fragment = LyricFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_lyric_layout

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}