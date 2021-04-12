package com.lpc.snowmusic.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.classic.common.MultipleStatusView
import com.gyf.immersionbar.ImmersionBar
import com.lpc.snowmusic.R
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseActivity
 * Desc:Activity基础类
 */
@SuppressWarnings("unchecked")
abstract class BaseActivity : AppCompatActivity() {
    //多种状态的 View 的切换
    protected var multipleStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initEventBus()
        initImmersionBar()
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
     * 是否使用EventBus  默认False
     */
    open fun useEventBus(): Boolean = false

    /**
     * 注册EventBus
     */
    protected open fun initEventBus() {
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()
    }

    /**
     * 初始化ToolBar
     */
    protected open fun initToolBar() {

    }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}