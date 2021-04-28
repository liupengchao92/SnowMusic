package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.utils.NavigationHelper
import kotlinx.android.synthetic.main.fragment_control_layout.*

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :ControlFragment
 * Desc:
 */
class ControlFragment : BaseFragment() {

    override fun getLayoutResId(): Int = R.layout.fragment_control_layout

    override fun initView(view: View) {
        //页面跳转
        bottom_control.setOnClickListener {
            NavigationHelper.navigateToPlaying(activity!!)
        }
    }

    override fun lazyLoad() {

    }
}