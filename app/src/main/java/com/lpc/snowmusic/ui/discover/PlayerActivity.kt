package com.lpc.snowmusic.ui.discover

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.SeekBar
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseFragment
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.database.loader.PlayLoveLoader
import com.lpc.snowmusic.event.MetaChangedEvent
import com.lpc.snowmusic.event.PlayModeEvent
import com.lpc.snowmusic.event.StatusChangedEvent
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.PlayContract
import com.lpc.snowmusic.mvp.presenter.PlayPresenter
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.player.PlayQueueManager
import com.lpc.snowmusic.ui.discover.adapter.PlayerAdapter
import com.lpc.snowmusic.ui.discover.fragment.CoverFragment
import com.lpc.snowmusic.ui.discover.fragment.LyricFragment
import com.lpc.snowmusic.utils.FormatUtil
import com.lpc.snowmusic.utils.MusicUtils
import com.lpc.snowmusic.utils.UIUtils
import com.lpc.snowmusic.widget.window.PlayQueueWindow
import kotlinx.android.synthetic.main.activity_player.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync

/**
 * 播放详情页面
 * */
class PlayerActivity : BaseMvpActivity<PlayContract.View, PlayContract.Presenter>(),
    PlayContract.View,
    SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private var music: Music? = null

    //封面Fragment
    private var coverFragment: CoverFragment? = null

    //歌词Fragment
    private var lyricFragment: LyricFragment? = null

    //适配器
    private val playerAdapter by lazy { PlayerAdapter(this) }

    override fun getLayoutResId(): Int = R.layout.activity_player

    override fun useEventBus(): Boolean = true

    override fun initToolBar() {
        //返回页面
        backIv.setOnClickListener { finish() }
        //歌曲操作
        operateSongIv.setOnClickListener { ToastUtils.showShort("") }
    }

    override fun initView() {
        super.initView()
        //添加Fragment
        coverFragment = CoverFragment.getInstance()
        lyricFragment = LyricFragment.getInstance()
        playerAdapter.addFragment(coverFragment as BaseFragment)
        playerAdapter.addFragment(lyricFragment as BaseFragment)
        //设置ViewPager
        viewPager2.run {
            adapter = playerAdapter
        }

        //
        seekProgress.setOnSeekBarChangeListener(this)

        playModeIv.setOnClickListener(this)
        prevPlayIv.setOnClickListener(this)
        playAndPause.setOnClickListener(this)
        nextPlayIv.setOnClickListener(this)
        playQueueIv.setOnClickListener(this)
        collectIv.setOnClickListener(this)
        downloadIv.setOnClickListener(this)
        shareIv.setOnClickListener(this)
        equalizerIv.setOnClickListener(this)
        playlistAddIv.setOnClickListener(this)
        songCommentTv.setOnClickListener(this)
    }


    override fun onServiceConnect() {
        super.onServiceConnect()
        //获取正在播放的音乐
        presenter?.getPlayingMusic()
        //当前播放状态
        PlayManager.isPlaying().let {
            updatePlayStatus(it)
            updatePlayMode()
        }
    }

    override fun createPresenter(): PlayContract.Presenter = PlayPresenter()

    override fun showPlayingMusic(music: Music) {
        this.music = music
        //歌曲标题
        songNameTv.text = music.title
        //歌手
        singerName.text = music.artist
        //背景
        GlideUtils.loadBigImageView(
            this,
            music.coverUri,
            music.type,
            backgroundIv,
            true,
            30,
            MusicUtils.PIC_SIZE_SMALL
        )
        //是否收藏
        PlayLoveLoader.isLoveMusic(music)?.let {
            this.music?.isLove = it
            collectIv.setImageResource(if (it) R.drawable.item_favorite_love else R.drawable.item_favorite)
        }
    }

    override fun showLyric(lyric: String?, init: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayStatus(isPlaying: Boolean) {
        if (isPlaying) {
            playAndPause.setImageResource(R.drawable.img_player_play)
            coverFragment?.resumeRotateAnimation()
        } else {
            playAndPause.setImageResource(R.drawable.img_player_pause)
            coverFragment?.stopRotateAnimation()
        }
    }

    override fun updatePlayMode() {
        super.updatePlayMode()
        UIUtils.updatePlayMode(playModeIv, false)
    }

    override fun updateProgress(progress: Long, max: Long) {
        //进度条最大值
        seekProgress.max = max.toInt()
        //当前进度
        seekProgress.progress = progress.toInt()
        //当前播放的时长
        currentTv.text = FormatUtil.formatTime(progress)
        //总时长
        totalTv.text = FormatUtil.formatTime(max)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        seekBar?.progress?.let {
            PlayManager.seekTo(it)
        }
    }

    override fun onClick(v: View?) {
        //防止快速点击
        if (UIUtils.isFastClick()) return
        //相关点击事件
        when (v?.id) {
            R.id.prevPlayIv -> {
                //上一首
                PlayManager.playPrev()
            }

            R.id.playAndPause -> {
                //播放或者暂停
                PlayManager.playAndPause()
            }

            R.id.nextPlayIv -> {
                //下一首
                PlayManager.playNext()
            }

            R.id.playModeIv -> {
                //播放模式
                PlayQueueManager.updatePlayMode()
            }
            //播放队列
            R.id.playQueueIv -> {
                PlayQueueWindow(this)?.showPopupWindow()
            }

            //收藏喜欢
            R.id.collectIv ->{
                onClickCollect()
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePlayStatus(event: StatusChangedEvent) {
        updateLoading(!event.isPrepared)
        updatePlayStatus(event.isPlaying)
        seekProgress.secondaryProgress = event.percent.toInt()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePlayModeEvent(event: PlayModeEvent) {
        updatePlayMode()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updatePlayingMusic(event: MetaChangedEvent) {
        updateProgress(0, 0)
        showPlayingMusic(event.music)
        coverFragment?.loadCover()
    }

    /**
     *
     * 是否正在加载*/
    private fun updateLoading(loading: Boolean) {
        loadingAv.visibility = if (loading) View.VISIBLE else View.GONE
    }

    /**
     * 收藏
     * */
    fun onClickCollect() {

        music?.let {
            collectIv.setImageResource(if (!it.isLove) R.drawable.item_favorite_love else R.drawable.item_favorite)
        }
        //动画
        ValueAnimator.ofFloat(1f, 1.3f, 0.8f, 1f).apply {
            duration = 600

            addUpdateListener {
                collectIv.scaleX = it.animatedValue as Float
                collectIv.scaleY = it.animatedValue as Float
            }

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    music?.let {
                        doAsync {
                            if (it.isLove) {
                                PlayLoveLoader.removeLove(music!!)
                            } else {
                                PlayLoveLoader.addLoveList(music!!)
                            }
                            it.isLove = !it.isLove
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
        }.start()
    }
}
