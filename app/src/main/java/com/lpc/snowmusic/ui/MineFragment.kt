package com.lpc.snowmusic.ui

import android.os.Bundle
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.constant.Constant
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :MineFragment
 * Desc:我的
 */
class MineFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val args = Bundle()
            args.putString(Constant.FRAGMENT_KEY, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_mine


    override fun initView(view: View) {
        val title: String = arguments?.getString(Constant.FRAGMENT_KEY) as String
        text_content.text = title
    }

    override fun lazyLoad() {

    }


}