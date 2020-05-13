package com.example.lpc.snowmusic.base

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :IPresenter
 * Desc:
 */
interface IPresenter<in V : IView> {
    /**
     * 绑定 View
     */
    fun attachView(view: V)
    /**
     * 解绑 View
     */
    fun detachView()
}