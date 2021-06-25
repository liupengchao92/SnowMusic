package com.lpc.snowmusic.widget.lrcview

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.constant.SPkeyConstant
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.utils.MMKVUtils
import com.lpc.snowmusic.utils.NavigationHelper
import com.rtugeek.android.colorseekbar.ColorSeekBar
import kotlin.math.abs

/**
 * Author: liupengchao
 * Date: 2021/6/22
 * ClassName :FloatLyricView
 * Desc:桌面歌词View
 */
class FloatLyricView(context: Context) : FrameLayout(context), View.OnClickListener {

    //窗口管理类
    private val windowManager: WindowManager by lazy { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    //窗口参数，用于移动窗口
    var windowManagerParams: WindowManager.LayoutParams? = null

    //跟视图
    private val mRootView by lazy {
        LayoutInflater.from(context).inflate(R.layout.float_lyric_view, this)
    }
    private val mPreButton by lazy { mRootView?.findViewById<ImageView>(R.id.btn_previous) }
    private val mPlayButton by lazy { mRootView?.findViewById<ImageView>(R.id.btn_play) }
    private val mNextButton by lazy { mRootView?.findViewById<ImageView>(R.id.btn_next) }
    private val mLockButton by lazy { mRootView?.findViewById<ImageView>(R.id.btn_lock) }
    private val mSettingsButton by lazy { mRootView?.findViewById<ImageView>(R.id.btn_settings) }
    private val mFrameBackground by lazy { mRootView?.findViewById<View>(R.id.small_bg) }

    private val mSettingLinearLayout by lazy { mRootView?.findViewById<View>(R.id.ll_settings) }
    private val mRelLyricView by lazy { mRootView?.findViewById<View>(R.id.rl_layout) }
    private val mLinLyricView by lazy { mRootView?.findViewById<View>(R.id.ll_layout) }

    private val mMusicButton by lazy { mRootView?.findViewById<ImageButton>(R.id.music_app) }
    private val mCloseButton by lazy { mRootView?.findViewById<ImageButton>(R.id.btn_close) }
    val lyricText by lazy { mRootView?.findViewById<LyricTextView>(R.id.lyric) }
    val title by lazy { mRootView?.findViewById<TextView>(R.id.music_title) }

    private val mSizeSeekBar by lazy { mRootView?.findViewById<SeekBar>(R.id.sb_size) }

    private val mColorSeekBar by lazy { mRootView?.findViewById<ColorSeekBar>(R.id.sb_color) }
    private val view by lazy { mRootView?.findViewById<FrameLayout>(R.id.small_window_layout) }


    //记录小悬浮窗的宽度
    var viewWidth: Int = 0

    //记录小悬浮窗的高度
    var viewHeight: Int = 0

    //当前手指在屏幕上横坐标X的值
    var inScreenX: Float = 0f

    //当前手指在屏幕上纵坐标的Y的值
    var inScreenY: Float = 0f

    //记录手指按下时，在屏幕上横坐标X的值
    var downInScreenX: Float = 0f

    //记录手指按下时，在屏幕上纵坐标Y的值
    var downInScreenY: Float = 0f

    //记录手指按下时,在小悬浮窗的View上的横坐标的值
    var inViewX: Float = 0f

    //记录手指按下时,在小悬浮窗的View上的纵坐标的值
    var inViewY: Float = 0f

    //状态栏的高度
    private var statusBarHeight: Int = 0

    //是否可以移动悬浮窗
    private var movement: Boolean = true

    //桌面歌词是否锁定
    private var isLock: Boolean = false

    //是否展示背景
    private var isShowBackground: Boolean = true

    //设置界面是否隐藏
    private var isHiddenSettings: Boolean = false

    init {

        viewWidth = view?.layoutParams?.width ?: 0
        viewHeight = view?.layoutParams?.height ?: 0

        mCloseButton?.setOnClickListener(this)
        mMusicButton?.setOnClickListener(this)
        mLockButton?.setOnClickListener(this)
        mPreButton?.setOnClickListener(this)
        mPlayButton?.setOnClickListener(this)
        mNextButton?.setOnClickListener(this)
        mSettingsButton?.setOnClickListener(this)

        //字体大小变化回调
        mSizeSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //设置字体大小
                lyricText?.setFontSizeScale(progress.toFloat())
                //存储字体大小
                MMKVUtils.putValue(SPkeyConstant.DESKTOP_LYRIC_SIZE, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        //颜色的变化
        mColorSeekBar?.setOnColorChangeListener { colorBarPosition, alphaBarPosition, color ->
            lyricText?.setFontColorScale(color)
            MMKVUtils.putValue(SPkeyConstant.DESKTOP_LYRIC_COLOR, color)
        }
    }

    /**
     * 默认窗口值
     * */
    fun setDefaultValue() {
        //设置字体大小
        val fontSize = MMKVUtils.getInt(SPkeyConstant.DESKTOP_LYRIC_SIZE)
        lyricText?.setFontSizeScale(fontSize?.toFloat() ?: 0f)
        mSizeSeekBar?.progress = fontSize ?: 0
        //设置字体颜色
        val fontColor = MMKVUtils.getInt(SPkeyConstant.DESKTOP_LYRIC_COLOR)
        lyricText?.setFontColorScale(fontColor ?: 0)
        mColorSeekBar?.color = fontColor ?: 0

        //桌面歌词是否锁定
        isLock = MMKVUtils.getBoolean(SPkeyConstant.DESKTOP_LYRIC_LOCK) as Boolean
        saveLock(isLock, false)
        //更新播放状态
        PlayManager.isPlaying().let {
            updatePlayStatus(it)
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.music_app -> {
                NavigationHelper.navigateToMain(context)
            }
            R.id.btn_close -> {
                //关闭桌面歌词弹窗
                PlayManager.showDesktopLyric(false)
            }
            R.id.btn_lock -> {
                isLock = !isLock
                saveLock(isLock, true)
            }
            R.id.btn_previous -> {
                //播放与暂停
                PlayManager.playPrev()
            }
            R.id.btn_play -> {
                //播放与暂停
                PlayManager.playAndPause()
                //更新播放状态
                updatePlayStatus(PlayManager.isPlaying())
            }
            R.id.btn_next -> {
                //下一首
                PlayManager.playNext()
            }
            R.id.btn_settings -> {
                //设置歌词字体
                isHiddenSettings = !isHiddenSettings
                mSettingLinearLayout?.visibility = if (isHiddenSettings) View.VISIBLE else View.GONE
            }
        }
    }

    /**
     * 锁住位置
     *
     * */
    private fun saveLock(isLock: Boolean, isToast: Boolean) {
        if (isToast) {
            //是否吐司提示
            ToastUtils.showShort(if (isLock) R.string.float_lock else R.string.float_unlock)
        }

        if (layoutParams != null) {
            val params = layoutParams as WindowManager.LayoutParams
            if (isLock) {
                movement = false
                if (!isShowBackground) {
                    params.flags =
                        (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    params.flags =
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                }
                mLockButton?.setImageResource(R.drawable.ic_lock)
            } else {
                movement = true
                params.flags =
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                mLockButton?.setImageResource(R.drawable.ic_unlock)
            }
            windowManager.updateViewLayout(this, params)
            MMKVUtils.putValue(SPkeyConstant.DESKTOP_LYRIC_LOCK, isLock)
        }

    }

    private fun showLyricBackground() {
        if (mRootView != null && !isLock) {
            isShowBackground = !isShowBackground
            if (isShowBackground) {
                mRelLyricView?.visibility = View.VISIBLE
                mLinLyricView?.visibility = View.VISIBLE
                mFrameBackground?.visibility = View.VISIBLE
            } else {
                mLinLyricView?.visibility = View.INVISIBLE
                mRelLyricView?.visibility = View.INVISIBLE
                mFrameBackground?.visibility = View.INVISIBLE
            }
        }
    }

    /**
     * 更新播放状态
     *
     * */
    fun updatePlayStatus(isPlaying: Boolean) {
        mPlayButton?.setImageResource(if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                inViewX = event.x
                inViewY = event.y

                inScreenX = event.rawX
                inScreenY = event.rawY - getStatusBarHeight()

                downInScreenX = event.rawX
                downInScreenY = event.rawY - getStatusBarHeight()

            }

            MotionEvent.ACTION_MOVE -> {
                inScreenX = event.rawX
                inScreenY = event.rawY - getStatusBarHeight()
                //更新悬浮窗的位置
                updateWindowPosition()
            }

            MotionEvent.ACTION_UP -> {
                //当移动的位置小于25时认为是单击事件
                if (abs(inScreenX - downInScreenX) < 10 && abs(inScreenY - downInScreenY) < 10) {
                    LogUtils.d("点击了悬浮窗")
                    showLyricBackground()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private fun updateWindowPosition() {
        if (movement) {
            windowManager?.updateViewLayout(
                this,
                (layoutParams as WindowManager.LayoutParams)?.apply {
                    x = (inScreenX - inViewX).toInt()
                    y = (inScreenY - inViewY).toInt()
                })
        }
    }


    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private fun getStatusBarHeight(): Int {
        if (statusBarHeight == 0) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val o = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = field.get(o) as Int
                statusBarHeight = resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return statusBarHeight
    }
}