package com.lpc.snowmusic.utils

import android.graphics.Typeface
import android.widget.TextView

/**
 * Author: liupengchao
 * Date: 2021/4/14
 * ClassName :FontUtils
 * Desc:字体工具类
 */
object FontUtils {
    /**
     *设置字体
     */
    fun setTypeface(textView: TextView) {
        try {
            val typeface: Typeface = Typeface.createFromAsset(textView.context.assets, "regulartypeface.ttf")
            textView.typeface = typeface
        } catch (exception: Exception) {

        }
    }
}