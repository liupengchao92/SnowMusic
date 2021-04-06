package com.lpc.snowmusic.ui.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.http.bean.BannerBean
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import com.lpc.snowmusic.mvp.presenter.DiscoverPresenter
import com.lpc.snowmusic.ui.adapter.MyBannerAdapter
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: 发现
 */
class DiscoverFragment : BaseMvpFragment<DiscoverContract.View, DiscoverContract.Presenter>(), DiscoverContract.View {
    //轮播图数据
    val mBanners: MutableList<BannerBean> = mutableListOf()
    //轮播图适配器
    val bannerAdapter: MyBannerAdapter by lazy {
        MyBannerAdapter(mBanners)
    }

    override fun createPresenter(): DiscoverContract.Presenter = DiscoverPresenter()

    companion object {
        fun getInstance(title: String): DiscoverFragment {
            val fragment = DiscoverFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_discover

    override fun initView(view: View) {
        super.initView(view)
    }

    override fun lazyLoad() {
        presenter?.loadBannerView()
    }

    override fun showBannerView(banners: MutableList<BannerBean>) {
        mBanners.clear()
        mBanners.addAll(banners)
        banner.apply {
            adapter = bannerAdapter
            indicator = CircleIndicator(activity)
            addBannerLifecycleObserver(this@DiscoverFragment)
        }
    }

    override fun showHotSinger() {
    }
}