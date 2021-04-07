package com.lpc.snowmusic.ui.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.mv.fragment.MvRecommendFragment

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :MvFragmentPagerAdapter
 * Desc:
 */
class MvFragmentPagerAdapter(context: Context, fm: FragmentManager) :

    FragmentStatePagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {

    private val fragments = mutableListOf<BaseFragment>()

    private var titles = context.resources.getStringArray(R.array.mv_tab_title)

    init {
        for ((position, title) in titles.withIndex()) {
            val fragment = when (position) {
                0 -> MvRecommendFragment.getInstance(title)
                1 -> MvRecommendFragment.getInstance(title)
                2 -> MvRecommendFragment.getInstance(title)
                else -> MvRecommendFragment.getInstance(title)

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