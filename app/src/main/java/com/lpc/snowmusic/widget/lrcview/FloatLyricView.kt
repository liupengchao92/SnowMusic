package com.lpc.snowmusic.widget.lrcview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.lpc.snowmusic.R
import com.lpc.snowmusic.constant.SPkeyConstant
import com.lpc.snowmusic.utils.MMKVUtils
import com.rtugeek.android.colorseekbar.ColorSeekBar

/**
 * Author: liupengchao
 * Date: 2021/6/22
 * ClassName :FloatLyricView
 * Desc:桌面歌词View
 */
class FloatLyricView(context: Context) : FrameLayout(context), View.OnClickListener {

    //窗口管理类
    private val windowManager: WindowManager by lazy { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

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


    private var mMovement: Boolean = false
    private var isHiddenSettings: Boolean = false

    init {

        viewWidth = view?.layoutParams?.width ?: 0
        viewHeight = view?.layoutParams?.height ?: 0

        mMovement = true
        isHiddenSettings = true

        mCloseButton?.setOnClickListener(this)
        mMusicButton?.setOnClickListener(this)
        mLockButton?.setOnClickListener(this)
        mPreButton?.setOnClickListener(this)
        mPlayButton?.setOnClickListener(this)
        mNextButton?.setOnClickListener(this)
        mSettingsButton?.setOnClickListener(this)

        //设置字体大小
        val fontSize = MMKVUtils.getInt(SPkeyConstant.DESKTOP_LYRIC_SIZE)
        lyricText?.setFontSizeScale(fontSize?.toFloat() ?: 0f)
        mSizeSeekBar?.progress = fontSize ?: 0
        //设置字体颜色
        val fontColor = MMKVUtils.getInt(SPkeyConstant.DESKTOP_LYRIC_COLOR)
        lyricText?.setFontColorScale(fontColor ?: 0)
        mColorSeekBar?.color = fontColor ?: 0


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
     * 锁住位置
     *
     * */
    private fun saveLock(isLock: Boolean, isToast: Boolean) {

    }


    override fun onClick(v: View?) {


    }
}