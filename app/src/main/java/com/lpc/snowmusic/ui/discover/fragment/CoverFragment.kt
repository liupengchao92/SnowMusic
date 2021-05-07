package com.lpc.snowmusic.ui.discover.fragment

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.player.PlayManager
import kotlinx.android.synthetic.main.fragment_player_cover.*

/**
 * Author: liupengchao
 * Date: 2021/4/26
 * ClassName :PlayerPlayFragment
 * Desc:封面Fragment
 */
class CoverFragment : BaseFragment() {

    companion object {
        fun getInstance(): CoverFragment {
            val fragment = CoverFragment()
            return fragment
        }
    }

    //旋转属性动画
    private var coverAnimator: ObjectAnimator? = null

    override fun getLayoutResId(): Int = R.layout.fragment_player_cover

    override fun initView(view: View) {
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

    }

    override fun lazyLoad() {
        PlayManager.getPlayingMusic()?.let {
            GlideUtils.loadImageView(context, it.coverUri, iv_cover)
            startRotateAnimation()
        }
    }

    /**
     * 加载视频封面
     * */
    fun loadCover(coverUrl: String) {

    }

    /**
     * 切换歌曲，开始旋转动画
     */
    fun startRotateAnimation() {
        coverAnimator?.cancel()
        coverAnimator?.start()
    }

    /**
     * 停止旋转
     */
    fun stopRotateAnimation() {
        coverAnimator?.pause()
    }

    /**
     * 继续旋转
     */
    fun resumeRotateAnimation() {
        coverAnimator?.isStarted?.let {
            if (it) coverAnimator?.resume() else coverAnimator?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        if (coverAnimator != null && coverAnimator?.isPaused!! && PlayManager.isPlaying()) {
            coverAnimator?.resume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coverAnimator?.cancel()
        coverAnimator = null
    }

    override fun onStop() {
        super.onStop()
        coverAnimator?.pause()
    }

}