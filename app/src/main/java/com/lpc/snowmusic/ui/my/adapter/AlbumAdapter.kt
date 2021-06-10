package com.lpc.snowmusic.ui.my.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.LocalAlbum
import com.lpc.snowmusic.imageload.GlideUtils

/**
 * Author: liupengchao
 * Date: 2021/6/10
 * ClassName :AlbumAdapter
 * Desc:专辑列表
 */
class AlbumAdapter(datas: MutableList<LocalAlbum>) :
    BaseQuickAdapter<LocalAlbum, BaseViewHolder>(R.layout.item_album, datas) {

    override fun convert(holder: BaseViewHolder, item: LocalAlbum) {
        //专辑名称
        holder.setText(R.id.tv_album_name, item.name)
        //专辑封面
        GlideUtils.loadImageView(context, item.cover, holder.getView<ImageView>(R.id.iv_cover))
    }
}