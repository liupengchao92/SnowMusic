package com.lpc.snowmusic.ui.my.fragment

import android.os.Bundle
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.constant.Extras
import kotlinx.android.synthetic.main.fragment_local_detail.*

/**
 * Author: liupengchao
 * Date: 2021/6/7
 * ClassName :SingleSongFragment
 * Desc:单曲
 */
class LocalDetailFragment : BaseFragment() {
    //类型
    private var type = 0

    override fun getLayoutResId(): Int = R.layout.fragment_local_detail


    companion object {
        fun getInstance(type: Int): BaseFragment {
            val bundle = Bundle();
            bundle.putInt(Extras.TYPE, type)
            val fragment = LocalDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(view: View) {
        type = arguments?.getInt(Extras.TYPE)!!
        contentTv.text=(type.toString())
    }

    override fun lazyLoad() {

    }
}

