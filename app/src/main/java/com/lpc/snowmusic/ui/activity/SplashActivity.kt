package com.lpc.snowmusic.ui.activity

import android.content.Intent
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Author: liupengchao
 * Date: 2020/5/13
 * ClassName :SplashActivity
 * Desc:启动页
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView() {
        //跳转
        jump.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
