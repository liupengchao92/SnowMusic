package com.lpc.snowmusic.ui.my.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.ui.my.fragment.LocalDetailFragment
import com.lpc.snowmusic.ui.my.fragment.LocalDetailFragment.Companion.ALBUM
import com.lpc.snowmusic.ui.my.fragment.LocalDetailFragment.Companion.ARTIST
import com.lpc.snowmusic.ui.my.fragment.LocalDetailFragment.Companion.FOLDER
import com.lpc.snowmusic.ui.my.fragment.LocalDetailFragment.Companion.SINGLE_SONG

/**
 * Author: liupengchao
 * Date: 2021/6/7
 * ClassName :LocalPagerAdapter
 * Desc:本地歌曲切页适配器
 */
class LocalPagerAdapter(fragmentActivity: FragmentActivity, val titles: Array<String>) :
    FragmentStateAdapter(fragmentActivity) {

    //Fragment
    private val fragments = mutableListOf<BaseFragment>()

    init {
        fragments.add(LocalDetailFragment.getInstance(SINGLE_SONG))
        fragments.add(LocalDetailFragment.getInstance(ARTIST))
        fragments.add(LocalDetailFragment.getInstance(ALBUM))
        fragments.add(LocalDetailFragment.getInstance(FOLDER))
    }

    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}


