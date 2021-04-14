package com.lpc.snowmusic.ui.discover.activity

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import com.blankj.utilcode.util.LogUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import com.lpc.snowmusic.constant.Extras
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_common_web_view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CommonWebViewActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_common_web_view

    override fun initImmersionBar() {

    }

    override fun initToolBar() {
        super.initToolBar()

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun initView() {
        val url: String = intent.getStringExtra(Extras.URL)
        webView.run {
            //加载网络地址
            loadUrl(url)
            //设置WebViewClient
            webViewClient = mWebViewClient
            //WebChromeClient
            webChromeClient = mWebChromeClient
            //通过addJavascriptInterface()将对象映射到JS对象
            addJavascriptInterface(ScriptObject(), "ScriptObject")

            //WebSettings
            settings.run {
                //设置编码格式
                defaultTextEncodingName = "utf-8"
                //设置js可用
                javaScriptEnabled = true
                //设置滚动条
                scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                //缓存模式
                cacheMode = WebSettings.LOAD_NO_CACHE
                //支持插件
                pluginsEnabled = true
                //将图片调整到适合WebView的大小
                useWideViewPort = true
                // 缩放至屏幕的大小
                loadWithOverviewMode = true
                //支持缩放
                setSupportZoom(true)
                //设置内置的缩放控件。若为false，则该WebView不可缩放
                builtInZoomControls = true
                //隐藏原生的缩放控件
                displayZoomControls = false
                //设置可以访问文件
                allowFileAccess = true
                //支持通过JS打开新窗口
                javaScriptCanOpenWindowsAutomatically = true
                //支持自动加载图片
                loadsImagesAutomatically = true
                //支持Dom 存储
                domStorageEnabled = true
                //处理http 和 https 图片混合的问题
                mixedContentMode = WebSettings.LOAD_NORMAL
            }
        }
    }

    //webViewClient
    private val mWebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            LogUtils.d("LoadUrl :$url")
            view?.loadUrl(url)
            return true
        }

        override fun onPageStarted(p0: WebView?, p1: String?, p2: Bitmap?) {
            super.onPageStarted(p0, p1, p2)
            //设定加载开始的操作
            LogUtils.d("WebView :onPageStarted")
        }

        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)
            //设定加载结束的操作
            LogUtils.d("WebView :onPageFinished")
        }

        override fun onReceivedError(p0: WebView?, p1: Int, p2: String?, p3: String?) {
            super.onReceivedError(p0, p1, p2, p3)
            LogUtils.e("WebView :ErrorCode=====>$p1   description : $p2")
        }

        override fun onReceivedSslError(webView: WebView?, handler: SslErrorHandler?, sslError: SslError?) {
            handler?.let {
                if (sslError?.primaryError == android.net.http.SslError.SSL_INVALID) {
                    // 校验过程遇到了bug
                    it.proceed()
                } else {
                    it.cancel()
                }
            }
        }
    }

    //
    private val mWebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(p0: WebView?, p1: String?) {
            if (!TextUtils.isEmpty(p1)) {
                toolbar.title = p1
            }
            super.onReceivedTitle(p0, p1)
        }

        override fun onProgressChanged(p0: WebView?, newProgress: Int) {
            super.onProgressChanged(p0, newProgress)
            if (newProgress == 100) {
                //加载完网页进度条消失
                progressBar.visibility = View.GONE
            } else {
                //开始加载网页时显示进度条
                progressBar.visibility = View.VISIBLE
                //设置进度值
                progressBar.progress = newProgress
            }
        }

        override fun onJsAlert(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
            return super.onJsAlert(p0, p1, p2, p3)
        }

        override fun onJsConfirm(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
            return super.onJsConfirm(p0, p1, p2, p3)
        }

        override fun onJsBeforeUnload(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
            return super.onJsBeforeUnload(p0, p1, p2, p3)
        }

        override fun onJsPrompt(p0: WebView?, p1: String?, p2: String?, p3: String?, p4: JsPromptResult?): Boolean {
            return super.onJsPrompt(p0, p1, p2, p3, p4)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    class ScriptObject
}
