package com.example.lpc.snowmusic.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :BaseModel
 * Desc:Model 基类
 */
abstract class BaseModel : IModel, LifecycleObserver {

    private var compositeDisposable: CompositeDisposable? = null

    override fun addDisposable(disposable: Disposable?) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }

        disposable?.let {
            compositeDisposable?.add(it)
        }

    }

    override fun clearDisposable() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestory(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}