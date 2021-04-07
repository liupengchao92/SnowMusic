package com.lpc.snowmusic.ui.mv.fragment

import android.os.Bundle
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :MvRecommendFragment
 * Desc:MV推荐
 */
class MvRecommendFragment : BaseFragment() {

    companion object {
        fun getInstance(title: String): MvRecommendFragment {
            val arguments = Bundle()
            arguments.putString("MvRecommendFragment", title)
            val fragment = MvRecommendFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_mv_recommend

    override fun initView(view: View) {
        /* arguments?.let {
             val title = it.getString("MvRecommendFragment")
             text.text = title
         }*/
    }

    override fun lazyLoad() {
    }

}