package com.lpc.snowmusic.ui.mv.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.MvInfoDetail
import com.lpc.snowmusic.mvp.contract.MvListContract
import com.lpc.snowmusic.mvp.presenter.MvListPresenter
import com.lpc.snowmusic.ui.mv.adapter.MvListAdapter
import com.lpc.snowmusic.utils.NavigationHelper
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_mv_recommend.*

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :MvRecommendFragment
 * Desc:MV Fragment
 */
class MvListFragment : BaseMvpFragment<MvListContract.View, MvListContract.Presenter>(),
    MvListContract.View {
    //
    private var position = 0

    //是否刷新
    private var isRefresh: Boolean = false

    //Mv数据
    private var mvListData = mutableListOf<MvInfoDetail>()

    //适配器
    private val mvListAdapter: MvListAdapter by lazy {
        MvListAdapter(mvListData)
    }

    //
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    //
    private val gridLayoutManager: GridLayoutManager by lazy {
        GridLayoutManager(activity, 2)
    }

    override fun createPresenter(): MvListContract.Presenter = MvListPresenter()

    companion object {
        //Key
        private const val kEY_POSITION: String = "position"

        fun getInstance(position: Int): MvListFragment {
            val arguments = Bundle()
            arguments.putInt(kEY_POSITION, position)
            val fragment = MvListFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_mv_recommend

    override fun initView(view: View) {
        super.initView(view)
        arguments?.let {
            position = it.getInt(kEY_POSITION)
        }

        //刷新
        smartRefreshLayout.run {
            setEnableRefresh(position == 1)
            setEnableLoadMore(position == 1)
            setOnRefreshListener(onRefreshListener)
            setOnLoadMoreListener(onLoadMoreListener)
        }
        //列表
        recyclerView.run {
            adapter = mvListAdapter
            if (position == 1) {
                layoutManager = linearLayoutManager
            } else {
                layoutManager = gridLayoutManager
            }
        }

        mvListAdapter.setOnItemClickListener { baseQuickAdapter, view, i ->
            val mvInfo :MvInfoDetail= baseQuickAdapter.data[i] as MvInfoDetail
            NavigationHelper.navigateToMvDetail(activity as FragmentActivity,mvInfo.id.toString())
        }
    }

    override fun lazyLoad() {
        presenter?.let {
            when (position) {
                0 -> it.loadPersonalizedMv()
                1 -> it.loadMv(0, 20)
                2 -> it.loadRecentMv(20)
            }
        }

    }

    override fun showMvList(mvList: MutableList<MvInfoDetail>) {
        if (isRefresh) {
            mvListData.clear()
            isRefresh = false
            smartRefreshLayout.finishRefresh()
        } else {
            if (mvList.size < 20) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                smartRefreshLayout.finishLoadMore()
            }
        }
        mvListData.addAll(mvList)
        mvListAdapter.run {
            setNewInstance(mvListData)
            notifyDataSetChanged()
        }
    }

    /**
     * 刷新监听
     * */
    private val onRefreshListener: OnRefreshListener = OnRefreshListener {
        presenter?.loadMv(0, 20)
        isRefresh = true
    }

    /**
     * 加载更多
     * */
    private val onLoadMoreListener: OnLoadMoreListener = OnLoadMoreListener {
        isRefresh = false
        presenter?.loadMv(mvListAdapter.itemCount, 20)
    }
}