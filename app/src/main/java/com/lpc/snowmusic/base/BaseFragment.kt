package com.lpc.snowmusic.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.classic.common.MultipleStatusView
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseFragment
 * Desc:Fragment 基类
 */
@SuppressWarnings("unchecked")
abstract class BaseFragment : Fragment() {
    //视图是否加载完毕
    var isViewPrepare = false
    //是否加载过数据
    var isHasLoad = false
    //多种状态的 View 的切换
    protected var multipleStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    /**
     * 布局 ID
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否加载数据
     */
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !isHasLoad) {
            lazyLoad()
            isHasLoad = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

}