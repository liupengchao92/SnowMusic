package com.lpc.snowmusic.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.cyl.musicapi.BaseApiImpl
import com.tencent.mmkv.MMKV
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import kotlin.properties.Delegates


/**
 * Author: liupengchao
 * Date: 2020/5/12
 * ClassName :MusicApplication
 * Desc:Application
 */
class MusicApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //初始化工具类
        Utils.init(this)
        //初始化
        BaseApiImpl.initWebView(this)
        //初始化腾讯X5内核
        initTBS()
        //初始化MMVK
        MMKV.initialize(context)
    }


    /**
     * 首次初始化冷启动优化
     * */
    private fun initTBS() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
    }
}