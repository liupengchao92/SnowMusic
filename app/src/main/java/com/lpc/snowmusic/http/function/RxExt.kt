package com.lpc.snowmusic.http.function

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.base.IModel
import com.lpc.snowmusic.base.IView
import com.lpc.snowmusic.http.bean.BaseBean
import com.lpc.snowmusic.http.exception.ErrorStatus
import com.lpc.snowmusic.http.exception.ExceptionHandle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Author: liupengchao
 * Date: 2020/5/27
 * ClassName :RxExt
 * Desc:Observable的扩展函数
 */
fun <T : BaseBean> Observable<T>.request(
    model: IModel?,
    view: IView,
    isShowLoading: Boolean = false,
    onSuccess: (T) -> Unit
) {

    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {

            override fun onComplete() {
                //取消弹窗
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) view.showLoading()
                model?.addDisposable(d)
                //检测网络
                if (!NetworkUtils.isConnected()) {
                    ToastUtils.showShort(MusicApplication.context.getString(R.string.network_unavailable_tip))
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when (t.errorCode) {
                    ErrorStatus.SUCCESS -> {
                        onSuccess.invoke(t)
                    }
                    ErrorStatus.TOKEN_INVALID -> {
                        //Token失效
                    }
                    else -> view?.showErrorMsg(t.errorMsg)
                }
            }

            override fun onError(e: Throwable) {
                view?.let {
                    it.hideLoading()
                    it.showErrorMsg(ExceptionHandle.handleException(e))
                }
            }
        })
}