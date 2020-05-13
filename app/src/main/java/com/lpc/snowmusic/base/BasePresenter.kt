package com.lpc.snowmusic.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BasePresenter
 * Desc:Presenter 基类
 */
@SuppressWarnings("unchecked")
abstract class BasePresenter<V : IView, M : IModel> : IPresenter<V>, LifecycleObserver {

    var view: V? = null

    var model: M? = null

    private val isViewAttached: Boolean
        get() = view != null

    /**
     * 使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    /**
     * 创建 Model
     */
    open fun createModel(): M? = null

    /**
     * 绑定 View
     */
    override fun attachView(view: V) {
        this.view = view
        model = createModel()
        if (view is LifecycleOwner) {
            view.lifecycle.addObserver(this)
            if (model != null && model is LifecycleOwner) {
                (model as LifecycleOwner).lifecycle.addObserver(this)
            }
        }

        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 解绑 View
     */
    override fun detachView() {
        model?.clearDisposable()
        model = null
        view = null
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * 检测 View 是否绑定
     */
    open fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() :
        RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

}