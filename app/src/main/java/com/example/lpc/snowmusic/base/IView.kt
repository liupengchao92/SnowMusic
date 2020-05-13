package com.example.lpc.snowmusic.base

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :IView
 * Desc:
 */
interface IView {
    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示信息
     */
    fun showMsg(msg: String)

    /**
     * 显示错误信息
     */
    fun showErrorMsg(errorMsg: String)

    /**
     * 显示空白页
     */
    fun showEmptyView()


}