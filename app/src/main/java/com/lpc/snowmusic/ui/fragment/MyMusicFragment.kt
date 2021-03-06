package com.lpc.snowmusic.ui.fragment

import android.os.Bundle
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.constant.Constant

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :MineFragment
 * Desc:我的
 */
class MyMusicFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): MyMusicFragment {
            val fragment = MyMusicFragment()
            val args = Bundle()
            args.putString(Constant.FRAGMENT_KEY, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_my_music


    override fun initView(view: View) {
        val title: String = arguments?.getString(Constant.FRAGMENT_KEY) as String

    }

    override fun lazyLoad() {

    }


}