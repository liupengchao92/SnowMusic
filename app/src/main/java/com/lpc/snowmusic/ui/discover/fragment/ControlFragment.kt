package com.lpc.snowmusic.ui.discover.fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.event.MetaChangedEvent
import com.lpc.snowmusic.event.PlayModeEvent
import com.lpc.snowmusic.event.StatusChangedEvent
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.mvp.presenter.PlayPresenter
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.utils.NavigationHelper
import com.lpc.snowmusic.widget.window.PlayQueueWindow
import kotlinx.android.synthetic.main.fragment_control_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :ControlFragment
 * Desc:
 */
class ControlFragment : BaseMvpFragment<PlayContract.View, PlayContract.Presenter>(), PlayContract.View {
    //动画实例
    private var coverAnimator: ObjectAnimator? = null

    override fun createPresenter(): PlayContract.Presenter = PlayPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_control_layout

    override fun useEventBus(): Boolean = true

    override fun initView(view: View) {
        super.initView(view)
        //初始化动画
        coverAnimator = ObjectAnimator.ofFloat(iv_cover, "rotation", 0f, 360f).apply {
            duration = 18000
            //线性转动
            interpolator = LinearInterpolator()
            //重复次数
            repeatCount = -1
            //重复模式
            repeatMode = ObjectAnimator.RESTART
        }
        //播放 暂停
        playPauseView.setOnClickListener { PlayManager.playAndPause() }
        //播放队列弹窗
        playQueueIv.setOnClickListener { PlayQueueWindow(activity as Context)?.showPopupWindow() }
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
        //更新播放状态
        updatePlayStatus(PlayManager.isPlaying())
    }

    override fun showLyric(lyric: String?, init: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        if (isPlaying) {
            playPauseView.updatePlayState(true)
            resumeRotateAnimation()
        } else {
            playPauseView.updatePlayState(false)
            stopRotateAnimation()
        }
    }

    override fun updateProgress(progress: Long, max: Long) {
        playPauseView.updateProgress(progress.toInt(), max.toInt())
    }

    /**
     * 停止旋转
     */
    private fun stopRotateAnimation() {
        coverAnimator?.pause()
    }

    /**
     * 继续旋转
     */
    private fun resumeRotateAnimation() {
        coverAnimator?.isStarted?.let {
            if (it) coverAnimator?.resume() else coverAnimator?.start()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePlayStatus(event: StatusChangedEvent) {
        updatePlayStatus(event.isPlaying)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePalyModeEvent(event: PlayModeEvent) {
        updatePlayMode()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePlayingMusic(event: MetaChangedEvent) {
        showPlayingMusic(event.music)
    }

}