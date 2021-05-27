package com.lpc.snowmusic.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.event.PlayListEvent
import com.lpc.snowmusic.mvp.contract.MyMusicContract
import com.lpc.snowmusic.mvp.presenter.MyMusicPresenter
import com.lpc.snowmusic.ui.my.activity.PlayHistoryActivity
import com.lpc.snowmusic.ui.my.activity.PlayLocalActivity
import kotlinx.android.synthetic.main.fragment_my_music.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.Serializable

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :MineFragment
 * Desc:我的
 */
class MyMusicFragment : BaseMvpFragment<MyMusicContract.View, MyMusicContract.Presenter>(),
    MyMusicContract.View,
    View.OnClickListener {
    //历史列表
    private var historyList = mutableListOf<Music>()

    override fun useEventBus(): Boolean = true

    override fun createPresenter(): MyMusicContract.Presenter = MyMusicPresenter()

    companion object {
        fun getInstance(title: String): MyMusicFragment {
            val fragment = MyMusicFragment()
            val args = Bundle()
            args.putString(Constants.FRAGMENT_KEY, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_my_music

    override fun initView(view: View) {
        super.initView(view)
        localItem.setOnClickListener(this)
        historyItem.setOnClickListener(this)
        favoriteItem.setOnClickListener(this)
        localMvItem.setOnClickListener(this)
        downloadItem.setOnClickListener(this)
    }

    override fun lazyLoad() {
        presenter?.loadSongs()
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.localItem -> {
                //本地歌曲
                val intent = Intent(activity, PlayLocalActivity::class.java)
                startActivity(intent)
            }
            R.id.historyItem -> {
                //播放历史
                val intent = Intent(activity, PlayHistoryActivity::class.java)
                intent.putExtra(Extras.PLAY_LIST, historyList as Serializable)
                startActivity(intent)
            }
            R.id.favoriteItem -> {
                //我的收藏
            }
            R.id.localMvItem -> {
                //本视频
            }
            R.id.downloadItem -> {
                //下载
            }
        }

    }


    override fun showSongs(songList: MutableList<Music>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLocalPlaylist(playlists: MutableList<Playlist>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPlaylist(playlists: MutableList<Playlist>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showWyPlaylist(playlists: MutableList<Playlist>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showHistory(musicList: MutableList<Music>) {
        historyList = musicList
        historyItem.setItemDesc(String.format(getString(R.string.song_num), musicList.size))
    }

    override fun showLoveList(musicList: MutableList<Music>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDownloadList(musicList: MutableList<Music>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 歌单发生变化
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayListChange(event: PlayListEvent) {
        (presenter as MyMusicPresenter)?.let {
            when (event.type) {
                Constants.PLAYLIST_HISTORY_ID -> {
                    it.loadHistoryList()
                }
            }
        }
    }
}