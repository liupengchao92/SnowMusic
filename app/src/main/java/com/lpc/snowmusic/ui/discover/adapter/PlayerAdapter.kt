package com.lpc.snowmusic.ui.discover.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.discover.PlayerActivity

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :PlayerAdapter
 * Desc:
 */
class PlayerAdapter(activity: PlayerActivity) : FragmentStateAdapter(activity) {
    //Fragment
    private var fragments = mutableListOf<BaseFragment>()

    fun addFragment(fragment: BaseFragment) {
        fragments.add(fragment)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}