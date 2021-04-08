package com.lpc.snowmusic.ui.main.fragment

import android.content.Context
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.main.adapter.MvFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_mv.*

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
        //初始化ViewPager
        viewpager.run {
            offscreenPageLimit = 3
            adapter = MvFragmentPagerAdapter(activity as Context, childFragmentManager!!)
            tab_layout.setupWithViewPager(this)
        }
        //

    }

    override fun lazyLoad() {
    }
}