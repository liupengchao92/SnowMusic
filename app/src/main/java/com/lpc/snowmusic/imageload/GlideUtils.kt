package com.lpc.snowmusic.imageload

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.lpc.snowmusic.R
import com.lpc.snowmusic.utils.MusicUtils
import jp.wasabeef.glide.transformations.BlurTransformation

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
    fun loadImageView(url: String?, imageView: ImageView?) {
        if (imageView == null) return
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.holder_with_bg)
            .placeholder(R.drawable.default_cover)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

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
            .placeholder(R.drawable.default_cover)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    fun loadImageView(mContext: Context?, url: String?, defaultUrl: Int, imageView: ImageView) {
        if (mContext == null) return
        Glide.with(mContext)
            .load(url)
            .error(defaultUrl)
            .placeholder(R.drawable.default_cover)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    /**
     * 加载获取Bitmap
     *
     * */
    fun loadImageBitmap(mContext: Context?, url: String?, target: CustomTarget<Bitmap>) {
        if (mContext == null) return
        Glide.with(mContext).asBitmap().load(url).into(target)
    }

    fun loadBigImageView(
        mContext: Context,
        url: String?,
        vendor: String?,
        imageView: ImageView?,
        isTransform: Boolean = false,
        sampling: Int = 3,
        size: Int = MusicUtils.PIC_SIZE_BIG

    ) {
        if (imageView == null) return
        val newUrl = MusicUtils.getAlbumPic(url, vendor, size)
        if (isTransform) {
            Glide.with(mContext)
                .asBitmap()
                .load(newUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, sampling)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        } else {
            Glide.with(mContext)
                .asBitmap()
                .load(newUrl)
                .placeholder(R.drawable.default_cover)
                .error(default_cover)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

}