package com.lpc.snowmusic.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.classic.common.MultipleStatusView
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseActivity
 * Desc:Activity基础类
 */
abstract class BaseActivity : AppCompatActivity() {

   //多种状态的 View 的切换
    protected var multipleStatusView: MultipleStatusView? = null

    /**
     * 是否使用EventBus  默认False
     */
    open fun useEventBus(): Boolean = false


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutResId())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initToolBar()
        initView()
        start()
    }

    /**
     * 布局文件ID
     */
    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    /**
     * 初始化ToolBar
     */
    protected open fun initToolBar() {}

    /**
     * 初始化View
     */
    protected abstract fun initView()

    /**
     * 开始请求
     */
    protected open fun start() {}


    override fun onDestroy() {
        super.onDestroy()

        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}