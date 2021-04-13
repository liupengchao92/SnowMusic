package com.lpc.snowmusic.ui.discover.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistFragmentAdapter
 * Desc:
 */
class ArtistFragmentAdapter(private val fragments: MutableList<BaseFragment>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}
