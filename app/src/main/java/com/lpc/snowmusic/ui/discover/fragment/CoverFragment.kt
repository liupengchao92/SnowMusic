package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :PlayerPlayFragment
 * Desc:封面Fragment
 */
class CoverFragment : BaseFragment() {

    companion object {
        fun getInstance(): CoverFragment {
            val fragment = CoverFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_player_cover

    override fun initView(view: View) {

    }

    override fun lazyLoad() {

    }
}