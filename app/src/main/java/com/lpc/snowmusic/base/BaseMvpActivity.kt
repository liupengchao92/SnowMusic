package com.lpc.snowmusic.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.utils.AnimationUtils.animateView

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseMvpActivity
 * Desc:MVP Activity基类
 */
@SuppressWarnings("unchecked")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    protected var presenter: P? = null

    private val contentView: ViewGroup by lazy {
        window.decorView.findViewById<ViewGroup>(android.R.id.content)
    }
    private val loadView: View by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false)
    }

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
        loadView.let {
            //加载视图
            contentView.addView(it)
            //动画
            animateView(it, true, 400)
        }
    }

    override fun hideLoading() {
        loadView.let {
            //加载视图
            contentView.removeView(it)
            //动画
            animateView(it, false, 400)
        }
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