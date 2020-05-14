package com.lpc.snowmusic.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.MineFragment

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :MainFragmentPagerAdapter
 * Desc:主页面PagerAdapter
 */
class MainFragmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<BaseFragment>()

    private val titles = arrayOf("我的", "发现", "排行榜", "MV")

    init {
        titles.forEach {
            fragments.add(MineFragment.getInstance(it))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}