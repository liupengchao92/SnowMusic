package com.lpc.snowmusic.ui.discover.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.discover.fragment.ArtistSongFragment

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistFragmentAdapter
 * Desc:
 */
class ArtistFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = mutableListOf<BaseFragment>()

    init {
        fragments.add(ArtistSongFragment.getInstance())
        fragments.add(ArtistSongFragment.getInstance())
        fragments.add(ArtistSongFragment.getInstance())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}
