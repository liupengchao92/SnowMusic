package com.lpc.snowmusic.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.ArtistInfo
import com.lpc.snowmusic.bean.BannerBean
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import com.lpc.snowmusic.mvp.presenter.DiscoverPresenter
import com.lpc.snowmusic.ui.adapter.HotSingerAdapter
import com.lpc.snowmusic.ui.adapter.MyBannerAdapter
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * Author: liupengchao
 * Date: 2020/5/17
 * ClassName :DiscoverFragment
 * Desc: 发现
 */
class DiscoverFragment : BaseMvpFragment<DiscoverContract.View, DiscoverContract.Presenter>(), DiscoverContract.View {
    //轮播图数据
    private val mBanners: MutableList<BannerBean> = mutableListOf()
    //热门歌手
    private val mArtists: MutableList<ArtistInfo> = mutableListOf()
    //轮播图适配器
    private val bannerAdapter: MyBannerAdapter by lazy {
        MyBannerAdapter(mBanners)
    }
    //热门歌手适配器
    private val hotSingerAdapter: HotSingerAdapter by lazy {
        HotSingerAdapter(mArtists)
    }
    //热门歌手
    private val hotSingerLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, 2, RecyclerView.HORIZONTAL, false)
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

        rv_hot_singer.run {
            layoutManager = hotSingerLayoutManager
            adapter = hotSingerAdapter
        }
    }

    override fun lazyLoad() {
        presenter?.loadBannerView()
        presenter?.getHotSinger(30, 0)
    }

    override fun showBannerView(banners: MutableList<BannerBean>) {
        mBanners.clear()
        mBanners.addAll(banners)
        banner.apply {
            adapter = bannerAdapter
            indicator = RectangleIndicator(activity)
            addBannerLifecycleObserver(this@DiscoverFragment)
            setBannerRound(4f)
        }
    }

    override fun showHotSinger(artists: MutableList<ArtistInfo>) {
        hotSingerAdapter.run {
            setNewInstance(artists)
            notifyDataSetChanged()
        }
    }
}