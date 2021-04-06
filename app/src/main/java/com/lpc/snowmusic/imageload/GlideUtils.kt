package com.lpc.snowmusic.imageload

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lpc.snowmusic.R

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :GlideUtils
 * Desc:
 */
object GlideUtils {

    /**
     * 显示图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    fun loadImageView(mContext: Context?, url: String?, imageView: ImageView?) {
        if (mContext == null) return
        if (imageView == null) return
        Glide.with(mContext)
            .load(url)
            .error(R.drawable.holder_with_bg)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    fun loadImageView(mContext: Context?, url: String?, defaultUrl: Int, imageView: ImageView) {
        if (mContext == null) return
        Glide.with(mContext)
            .load(url)
            .error(defaultUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

}