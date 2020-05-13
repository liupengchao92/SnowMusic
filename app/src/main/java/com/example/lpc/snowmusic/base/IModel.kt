package com.example.lpc.snowmusic.base

import io.reactivex.disposables.Disposable

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :IModel
 * Desc:
 */
interface IModel {
    /**
     * 添加订阅
     */
    fun addDisposable(disposable: Disposable?)


    /**
     * 清除订阅
     */
    fun clearDisposable()
}