package com.lpc.snowmusic.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.lpc.snowmusic.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Author: liupengchao
 * Date: 2020/5/13
 * ClassName :SplashActivity
 * Desc:启动页
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        jump.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
