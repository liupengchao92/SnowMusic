package com.lpc.snowmusic.ui.my.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.LocalArtist
import com.lpc.snowmusic.imageload.GlideUtils

/**
 * Author: liupengchao
 * Date: 2021/6/10
 * ClassName :ArtistAdapter
 * Desc:
 */
class ArtistAdapter(datas:MutableList<LocalArtist>):BaseQuickAdapter<LocalArtist,BaseViewHolder>(R.layout.item_artist,datas) {

    override fun convert(holder: BaseViewHolder, item: LocalArtist) {
        //加载头像
        GlideUtils.loadImageView(context,item.picUrl,holder.getView(R.id.iv_head))
        //名称
        holder.setText(R.id.tv_name,item.name)
    }
}