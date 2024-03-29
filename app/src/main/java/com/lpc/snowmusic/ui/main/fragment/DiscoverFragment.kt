package com.lpc.snowmusic.ui.main.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.BannerBean
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.mvp.contract.DiscoverContract
import com.lpc.snowmusic.mvp.presenter.DiscoverPresenter
import com.lpc.snowmusic.ui.discover.activity.CommonWebViewActivity
import com.lpc.snowmusic.ui.discover.activity.SongListSquareActivity
import com.lpc.snowmusic.ui.main.adapter.HotSingerAdapter
import com.lpc.snowmusic.ui.main.adapter.MyBannerAdapter
import com.lpc.snowmusic.ui.main.adapter.RecommendAdapter
import com.lpc.snowmusic.utils.NavigationHelper
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener
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
    private val mArtists: MutableList<Artist> = mutableListOf()
    //推荐列表
    private val mRecommends: MutableList<Playlist> = mutableListOf()
    //轮播图适配器
    private val bannerAdapter: MyBannerAdapter by lazy {
        MyBannerAdapter(mBanners)
    }
    //热门歌手适配器
    private val hotSingerAdapter: HotSingerAdapter by lazy {
        HotSingerAdapter(mArtists)
    }
    //推荐歌单适配器
    private val recommendAdapter: RecommendAdapter by lazy {
        RecommendAdapter(mRecommends)
    }
    //热门歌手
    private val hotSingerLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, 2, RecyclerView.HORIZONTAL, false)
    }
    //推荐歌单
    private val recommendLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, 3)
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
        //轮播图
        banner.run {
            adapter = bannerAdapter
            indicator = RectangleIndicator(activity)
            addBannerLifecycleObserver(this@DiscoverFragment)
            setBannerRound(4f)
            adapter.setOnBannerListener(OnBannerListener<BannerBean> { data, position ->
                LogUtils.d("targetType  :${data.targetType}")
                when (data.targetType) {
                    "3000" -> {
                        val intent = Intent(context, CommonWebViewActivity::class.java).apply {
                            putExtra(Extras.URL, data.url)
                        }
                        activity?.startActivity(intent)
                    }
                    "10" -> {
                        //专辑

                    }
                    "1000" -> {
                        //歌单
                        NavigationHelper.navigateToSongDetail(context, Playlist().apply {
                            pid = data.targetId
                            coverUrl = data.picUrl
                            type = Constants.PLAYLIST_WY_ID
                        })
                    }
                    "1004" -> {
                        //mv

                    }
                    "1" -> {
                        //单曲

                    }
                }
            })
        }
        //热门歌手
        rv_hot_singer.run {
            layoutManager = hotSingerLayoutManager
            adapter = hotSingerAdapter
            //OnItemClickListener
            hotSingerAdapter.setOnItemClickListener { adapter, view, position ->
                val artist = adapter.data[position] as Artist
                NavigationHelper.navigateToArtistDetail(context, artist)
            }
        }
        //推荐歌单
        rv_recommend.run {
            layoutManager = recommendLayoutManager
            adapter = recommendAdapter
            // addItemDecoration(SpaceItemDecoration(SizeUtils.dp2px(10f)))
        }

        //
        tv_song_square.setOnClickListener {
            val intent = Intent(activity, SongListSquareActivity::class.java)
            startActivity(intent)
        }

    }

    override fun lazyLoad() {
        presenter?.loadBannerView()
        presenter?.getHotSinger(30, 0)
        presenter?.getRecommend()
    }

    override fun showLoading() {
        LogUtils.d("showLoading=========>")
        multiple_StatusView?.showLoading()
    }

    override fun hideLoading() {
        multiple_StatusView.showContent()
    }

    override fun showBannerView(banners: MutableList<BannerBean>) {
        mBanners.clear()
        mBanners.addAll(banners)
        bannerAdapter.setDatas(mBanners)
        bannerAdapter.notifyDataSetChanged()
    }

    override fun showHotSinger(artists: MutableList<Artist>) {
        hotSingerAdapter.run {
            setNewInstance(artists)
            notifyDataSetChanged()
        }
    }

    override fun showRecommendList(result: MutableList<Playlist>) {
        recommendAdapter.run {
            mRecommends.clear()
            if (result.size > 6) {
                mRecommends.addAll(result.subList(0, 6))
            } else {
                mRecommends.addAll(result)
            }
            setNewInstance(mRecommends)
            notifyDataSetChanged()

            setOnItemClickListener { adapter, view, position ->
                val playlist = adapter.data[position] as Playlist
                NavigationHelper.navigateToSongDetail(context!!, playlist)
            }
        }
    }
}