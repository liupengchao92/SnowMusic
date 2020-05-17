package com.lpc.snowmusic.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.fragment.DiscoverFragment
import com.lpc.snowmusic.ui.fragment.MvFragment
import com.lpc.snowmusic.ui.fragment.MyMusicFragment
import com.lpc.snowmusic.ui.fragment.TopFragment

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :MainFragmentPagerAdapter
 * Desc:主页面PagerAdapter
 */
class MainFragmentPagerAdapter(context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<BaseFragment>()

    private var titles = context.resources.getStringArray(R.array.main_tab_title)

    init {
        for ((position, title) in titles.withIndex()) {
            val fragment = when (position) {
                0 -> MyMusicFragment.getInstance(title)
                1 -> DiscoverFragment.getInstance(title)
                2 -> TopFragment.getInstance(title)
                else -> MvFragment.getInstance(title)

            }
            fragments.add(fragment)
        }
    }

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}