package com.lpc.snowmusic.utils

import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.LogUtils
import net.lucode.hackware.magicindicator.MagicIndicator

/**
 * Author: liupengchao
 * Date: 2021/6/8
 * ClassName :ViewPager2Helper
 * Desc:MagicIndicator 与 ViewPager2绑定
 */
object ViewPager2Helper {

    /**
     *绑定
     *
     * */
    fun bind(magicIndicator: MagicIndicator, viewPager2: ViewPager2) {

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                magicIndicator?.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                magicIndicator?.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                magicIndicator?.onPageScrollStateChanged(state)
            }
        })
    }
}