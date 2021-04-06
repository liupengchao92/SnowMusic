package com.lpc.snowmusic.ui.fragment

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import com.lpc.snowmusic.mvp.presenter.DiscoverPresenter

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: 发现
 */
class DiscoverFragment : BaseMvpFragment<DiscoverContract.View, DiscoverContract.Presenter>() {

    override fun createPresenter(): DiscoverContract.Presenter = DiscoverPresenter()


    companion object {
        fun getInstance(title: String): DiscoverFragment {
            val fragment = DiscoverFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_discover

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
        LogUtils.d("lazyLoad")
        presenter?.loadBannerView()
    }
}