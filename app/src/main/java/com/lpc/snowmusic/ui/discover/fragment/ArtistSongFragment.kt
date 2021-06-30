package com.lpc.snowmusic.ui.discover.fragment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.ui.discover.adapter.SongAdapter
import com.lpc.snowmusic.utils.NavigationHelper
import kotlinx.android.synthetic.main.fragment_artist_song.*

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :ArtistSongFragment
 * Desc:歌曲
 */
class ArtistSongFragment : BaseFragment() {
    var artistID: String? = "0"
    //歌曲数据
    private val songs = mutableListOf<Music>()
    //歌曲适配器
    private val songAdapter: SongAdapter by lazy {
        SongAdapter(songs)
    }

    override fun getLayoutResId(): Int = R.layout.fragment_artist_song

    override fun initView(view: View) {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = songAdapter
            itemAnimator = DefaultItemAnimator()
        }

        songAdapter.setOnItemClickListener { adapter, view, position ->
            PlayManager.play(adapter.data as List<Music>, position, artistID.toString())
            NavigationHelper.navigateToPlaying(activity as Context)
            songAdapter.notifyDataSetChanged()
        }
    }

    override fun lazyLoad() {

    }

    /**
     * 设置数据
     * */
    fun showSongList(songs: MutableList<Music>) {
        songAdapter.run {
            setNewInstance(songs)
            notifyDataSetChanged()
        }
    }
}