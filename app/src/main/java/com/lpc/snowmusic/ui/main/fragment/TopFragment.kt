package com.lpc.snowmusic.ui.main.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: 排行榜
 */
class TopFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): TopFragment {
            val fragment = TopFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_top

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}