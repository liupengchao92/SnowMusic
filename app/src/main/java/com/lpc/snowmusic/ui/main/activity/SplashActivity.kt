package com.lpc.snowmusic.ui.main.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
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
            PermissionX.init(this as FragmentActivity)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request(object : RequestCallback {
                    override fun onResult(
                        allGranted: Boolean,
                        grantedList: MutableList<String>?,
                        deniedList: MutableList<String>?
                    ) {
                        if (allGranted) {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        } else {
                            ToastUtils.showShort("您拒绝了如下权限：$deniedList")
                        }
                    }
                })

        }
    }
}
