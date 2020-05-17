package com.lpc.snowmusic.ui.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: MV
 */
class MvFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): MvFragment {
            val fragment = MvFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_mv

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}