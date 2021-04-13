package com.lpc.snowmusic.ui.discover.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.utils.ConvertUtils

/**
 * Author: liupengchao
 * Date: 2021/4/13
 * ClassName :SongAdapter
 * Desc:歌曲列表适配器
 */
class SongAdapter(songs: MutableList<Music>) : BaseQuickAdapter<Music, BaseViewHolder>(R.layout.item_music, songs) {
    override fun convert(holder: BaseViewHolder, item: Music) {
        //封面
        GlideUtils.loadImageView(context, item.coverUri, holder.getView(R.id.iv_cover))
        //歌曲名称
        holder.setText(R.id.tv_title, ConvertUtils.getTitle(item.title))
        //音质图标显示
        val quality = when {
            item.sq -> context.resources.getDrawable(R.drawable.sq_icon, null)
            item.hq -> context.resources.getDrawable(R.drawable.hq_icon, null)
            else -> null
        }
        quality?.let {
            quality.setBounds(0, 0, quality.minimumWidth + SizeUtils.dp2px(2f), quality.minimumHeight)
            holder.getView<TextView>(R.id.tv_artist).setCompoundDrawables(quality, null, null, null)
        }
        //设置歌手专辑名
        holder.setText(R.id.tv_artist, ConvertUtils.getArtistAndAlbum(item.artist, item.album))
        //是否有mv（现只支持百度音乐）
        if (item.hasMv == 1) {
            holder.getView<View>(R.id.iv_mv).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.iv_mv).visibility = View.GONE
        }
        //是否可播放
        if (item.isCp) {
            holder.setTextColor(R.id.tv_title, ContextCompat.getColor(context, R.color.grey))
            holder.setTextColor(R.id.tv_artist, ContextCompat.getColor(context, R.color.grey))
        }

    }
}