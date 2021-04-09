package com.lpc.snowmusic.imageload

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lpc.snowmusic.R
import com.lpc.snowmusic.utils.MusicUtils

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :GlideUtils
 * Desc:
 */
object GlideUtils {

    val default_cover: Int = R.drawable.default_cover

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

    fun loadBigImageView(mContext: Context, url: String?, vendor: String?, imageView: ImageView?) {
        if (imageView == null) return
        val newUrl = MusicUtils.getAlbumPic(url, vendor, MusicUtils.PIC_SIZE_BIG)
        Glide.with(mContext)
            .asBitmap()
            .load(newUrl)
            .error(default_cover)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

}