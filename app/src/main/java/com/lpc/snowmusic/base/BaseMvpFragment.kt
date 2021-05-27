package com.lpc.snowmusic.base

import android.content.Context
import android.view.View
import com.blankj.utilcode.util.ToastUtils

/**
 * Author: liupengchao
 * Date: 2020/5/13
 * ClassName :BaseMvpFragment
 * Desc:MVP Fragment基类
 */
abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected var presenter: P? = null

    /**
     * 创建Presenter实例
     */
    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        presenter = createPresenter()
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showErrorMsg(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun showEmptyView() {
        multipleStatusView?.showEmpty()
    }

    override fun getViewContext(): Context = context!!

}

