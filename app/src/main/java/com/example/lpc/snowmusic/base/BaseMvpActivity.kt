package com.example.lpc.snowmusic.base

import com.blankj.utilcode.util.ToastUtils

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseMvpActivity
 * Desc:MVP Activity基类
 */
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    protected var presenter: P? = null

    /**
     * 创建Presenter实例
     */
    protected abstract fun createPresenter(): P

    override fun initView() {
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
}