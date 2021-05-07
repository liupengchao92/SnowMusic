package com.lpc.snowmusic.http.function

/**
 * Author: liupengchao
 * Date: 2021/5/6
 * ClassName :RequestCallBack
 * Desc:请求回调
 */
interface RequestCallBack<T> {

    fun onSuccess(t: T)

    fun onError(e: Throwable)
}