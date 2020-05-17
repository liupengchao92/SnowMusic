package com.lpc.snowmusic.ui.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: 发现
 */
class DiscoverFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): DiscoverFragment {
            val fragment = DiscoverFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_discover

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}