package com.lpc.snowmusic.ui.my.fragment

import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import androidx.recyclerview.widget.LinearLayoutManager
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.mvp.contract.LocalDetailContract
import com.lpc.snowmusic.mvp.presenter.LocalDetailPresenter
import com.lpc.snowmusic.ui.discover.adapter.SongAdapter
import kotlinx.android.synthetic.main.fragment_local_detail.*

/**
 * Author: liupengchao
 * Date: 2021/6/7
 * ClassName :SingleSongFragment
 * Desc:单曲
 */
class LocalDetailFragment :
    BaseMvpFragment<LocalDetailContract.View, LocalDetailContract.Presenter>(),
    LocalDetailContract.View {
    //类型
    private var type = 0

    //歌曲列表
    private var songList = mutableListOf<Music>()

    //单曲适配器
    private val songAdapter by lazy { SongAdapter(songList) }

    override fun getLayoutResId(): Int = R.layout.fragment_local_detail

    override fun createPresenter(): LocalDetailContract.Presenter = LocalDetailPresenter()

    companion object {
        //单曲
        const val SINGLE_SONG = 0

        //歌手
        const val ARTIST = 1

        //专辑
        const val ALBUM = 2

        //文件夹
        const val FOLDER = 3

        fun getInstance(type: Int): BaseFragment {
            val bundle = Bundle();
            bundle.putInt(Extras.TYPE, type)
            val fragment = LocalDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(view: View) {
        super.initView(view)

        type = arguments?.getInt(Extras.TYPE)!!

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            overScrollMode = OVER_SCROLL_NEVER
            adapter = when (type) {

                SINGLE_SONG -> songAdapter

                else -> null
            }
        }
    }

    override fun lazyLoad() {
        when (type) {
            SINGLE_SONG -> presenter?.getLocalSongs(context!!)
        }
    }


    override fun showSongList(songList: MutableList<Music>) {
        songAdapter.setNewInstance(songList)
        songAdapter.notifyDataSetChanged()
    }
}

