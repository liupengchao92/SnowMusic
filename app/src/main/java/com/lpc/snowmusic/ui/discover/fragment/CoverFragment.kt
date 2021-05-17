package com.lpc.snowmusic.ui.discover.fragment

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.FragmentActivity
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.utils.NavigationHelper
import com.lpc.snowmusic.widget.window.MusicQualitySelectWindow
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


        //音质
        qualityTv.setOnClickListener {
            PlayManager.getPlayingMusic()?.let {
                val selectWindow = MusicQualitySelectWindow(context!!, it)
                selectWindow.onQualitySelectListener = {
                    qualityTv.text = it
                }
                selectWindow.showPopupWindow()
            }

        }
        //音效
        soundEffectTv.setOnClickListener {
            activity.let {
                NavigationHelper.navigateToSoundEffect(it as FragmentActivity)
            }
        }

    }

    override fun lazyLoad() {
        loadCover()
    }

    /**
     * 加载视频封面
     * */
    fun loadCover() {
        PlayManager.getPlayingMusic()?.let {
            //加载封面
            GlideUtils.loadImageView(context, it.coverUri, iv_cover)
            //更新类型
            updateMusicType(it)
        }
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


    /**
     * 歌曲类型
     */
    fun updateMusicType(playingMusic: Music) {
        //音乐类型
        typeDescTv.text = when (playingMusic.type) {
            Constants.QQ -> getString(R.string.res_qq)
            Constants.BAIDU -> getString(R.string.res_baidu)
            Constants.XIAMI -> getString(R.string.res_xiami)
            Constants.NETEASE -> getString(R.string.res_wangyi)
            else -> getString(R.string.res_local)
        }
        //音质
        qualityTv.text = when (playingMusic.quality) {
            128000 -> getString(R.string.sound_quality_standard)
            192000 -> getString(R.string.sound_quality_high)
            320000 -> getString(R.string.sound_quality_hq_high)
            999000 -> getString(R.string.sound_quality_sq_high)
            else -> getString(R.string.sound_quality_standard)
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