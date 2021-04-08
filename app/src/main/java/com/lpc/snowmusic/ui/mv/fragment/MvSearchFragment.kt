package com.lpc.snowmusic.ui.mv.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.MvInfoDetail
import com.lpc.snowmusic.mvp.contract.MvListContract
import com.lpc.snowmusic.mvp.presenter.MvListPresenter
import com.lpc.snowmusic.ui.mv.adapter.MvListAdapter
import kotlinx.android.synthetic.main.fragment_mv_search.*

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :MvSearchFragment
 * Desc:Mv搜索
 */
class MvSearchFragment : BaseMvpFragment<MvListContract.View, MvListContract.Presenter>(), MvListContract.View {
    //是否加载更多
    private var isLoadMore: Boolean = false
    //搜索结果数据
    private val results = mutableListOf<MvInfoDetail>()
    //适配器
    private val mvListAdapter: MvListAdapter by lazy {
        MvListAdapter(results)
    }
    //
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    companion object {
        fun getInstance(): BaseFragment = MvSearchFragment()
    }

    override fun createPresenter(): MvListContract.Presenter = MvListPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_mv_search

    override fun initView(view: View) {
        super.initView(view)

        smartRefreshLayout.setOnLoadMoreListener {
            isLoadMore = true
            val keyWord = searchEditText.text.trim().toString()
            presenter?.searchMv(keyWord, mvListAdapter.itemCount)
        }

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mvListAdapter
        }

        tv_search.setOnClickListener {
            isLoadMore = false
            val keyWord = searchEditText.text.trim().toString()
            presenter?.searchMv(keyWord, 0)
        }
    }

    override fun lazyLoad() {
    }

    override fun showLoading() {
        loading_progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading_progress_bar.visibility = View.GONE
    }

    override fun showMvList(mvList: MutableList<MvInfoDetail>) {
        if (mvList.isEmpty()) {
            empty.visibility = View.VISIBLE
        } else {
            empty.visibility = View.GONE
        }
        if (isLoadMore) {
            results.addAll(mvList)
        } else {
            results.clear()
            results.addAll(mvList)
        }

        if (mvList.size < 20) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            smartRefreshLayout.finishLoadMore()
        }
        mvListAdapter.run {
            setNewInstance(results)
            notifyDataSetChanged()
        }
    }

}