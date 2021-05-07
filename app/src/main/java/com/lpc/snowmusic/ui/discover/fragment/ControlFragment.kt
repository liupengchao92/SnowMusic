package com.lpc.snowmusic.ui.discover.fragment

import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.mvp.presenter.PlayPresenter
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.utils.NavigationHelper
import kotlinx.android.synthetic.main.fragment_control_layout.*

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :ControlFragment
 * Desc:
 */
class ControlFragment : BaseMvpFragment<PlayContract.View, PlayContract.Presenter>(), PlayContract.View {

    override fun createPresenter(): PlayContract.Presenter = PlayPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_control_layout

    override fun initView(view: View) {
        //页面跳转
        bottom_control.setOnClickListener {
            NavigationHelper.navigateToPlaying(activity!!)
        }
    }

    override fun lazyLoad() {
        presenter?.getPlayingMusic()
    }

    override fun showPlayingMusic(music: Music) {
        //播放的封面
        GlideUtils.loadImageView(context, music.coverUri, iv_cover)
        //正在播放的歌曲名称
        songNameTv.text = music.title
    }

    override fun showLyric(lyric: String?, init: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        if (PlayManager.isPlaying()) {

        }
    }

    override fun updateProgress(progress: Long, max: Long) {


    }

}