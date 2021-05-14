package com.lpc.snowmusic.ui.discover.fragment

import android.text.TextUtils
import android.view.View
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.event.LyricUpdateEvent
import com.lpc.snowmusic.player.FloatLyricViewManager
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.player.PlayProgressListener
import com.lpc.snowmusic.player.ProgressHelper
import com.lpc.snowmusic.widget.lrcview.LrcView
import kotlinx.android.synthetic.main.fragment_lyric_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :LyricFragment
 * Desc:歌词显示
 * */
class LyricFragment : BaseFragment(), PlayProgressListener {


    companion object {
        fun getInstance(): LyricFragment {
            val fragment = LyricFragment()
            return fragment
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_lyric_layout

    override fun useEventBus(): Boolean = true

    override fun initView(view: View) {
        lrcView.run {
            setDraggable(true, object : LrcView.OnPlayClickListener {
                override fun onPlayClick(view: LrcView?, time: Long): Boolean {
                    PlayManager.seekTo(time as Int)
                    PlayManager.playAndPause()
                    return true
                }
            })

        }

    }

    override fun lazyLoad() {
        if (!TextUtils.isEmpty(FloatLyricViewManager.lyricInfo)) {
            showLyric(FloatLyricViewManager.lyricInfo)
        }
    }

    override fun onResume() {
        super.onResume()
        ProgressHelper.addProgressListener(this)
    }

    override fun onPause() {
        super.onPause()
        ProgressHelper.removeProgressListener(this)
    }

    /**
     * 加载歌词
     * */
    private fun showLyric(lyric: String) {
        lrcView?.loadLrc(lyric)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateLyric(event: LyricUpdateEvent) {
        showLyric(event.lyric)
    }

    override fun onProgressUpdate(position: Long, duration: Long) {
        lrcView?.updateTime(position)
    }
}