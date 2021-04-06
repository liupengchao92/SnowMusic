package com.lpc.snowmusic.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.ArtistInfo
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.utils.MusicUtils

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :HotSingerAdapter
 * Desc:热门歌手
 */
class HotSingerAdapter(datas: MutableList<ArtistInfo>) :
    BaseQuickAdapter<ArtistInfo, BaseViewHolder>(R.layout.item_discover_singer, datas) {

    override fun convert(holder: BaseViewHolder, item: ArtistInfo) {
        //歌手名称
        holder.setText(R.id.tv_name, item.name)
        //歌手头像
        GlideUtils.loadImageView(
            context,
            MusicUtils.getAlbumPic(item.picUrl, Constants.NETEASE, MusicUtils.PIC_SIZE_SMALL),
            holder.getView(R.id.iv_cover)
        )
    }
}