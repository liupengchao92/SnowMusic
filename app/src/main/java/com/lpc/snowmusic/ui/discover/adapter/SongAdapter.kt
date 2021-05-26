package com.lpc.snowmusic.ui.discover.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Constants
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.utils.ConvertUtils

/**
 * Author: liupengchao
 * Date: 2021/4/13
 * ClassName :SongAdapter
 * Desc:歌曲列表适配器
 */
class SongAdapter(songs: MutableList<Music>) :
    BaseQuickAdapter<Music, BaseViewHolder>(R.layout.item_music, songs) {
    override fun convert(holder: BaseViewHolder, item: Music) {
        //封面
        GlideUtils.loadImageView(context, item.coverUri, holder.getView(R.id.iv_cover))
        //歌曲名称
        holder.setText(R.id.tv_title, ConvertUtils.getTitle(item.title))
        //是否正在播放的歌曲
        if (item.mid == PlayManager.getPlayingMusic()?.mid) {
            holder.getView<View>(R.id.v_playing).visibility = View.VISIBLE
            holder.setTextColorRes(R.id.tv_title,R.color.color_09bb07)
        } else {
            holder.getView<View>(R.id.v_playing).visibility = View.GONE
            holder.setTextColorRes(R.id.tv_title,R.color.common_black_text)
        }
        //音质图标显示
        val quality = when {
            item.sq -> context.resources.getDrawable(R.drawable.sq_icon, null)
            item.hq -> context.resources.getDrawable(R.drawable.hq_icon, null)
            else -> null
        }
        quality?.let {
            quality.setBounds(
                0,
                0,
                quality.minimumWidth + SizeUtils.dp2px(2f),
                quality.minimumHeight
            )
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

        //歌曲类型,改变左边小图标
        if (item.type == Constants.LOCAL) {
            holder.getView<View>(R.id.iv_resource).visibility = View.GONE
        } else {
            holder.getView<View>(R.id.iv_resource).visibility = View.VISIBLE
            when {
                item.type == Constants.BAIDU -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.baidu)
                }
                item.type == Constants.NETEASE -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.netease)
                }
                item.type == Constants.QQ -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.qq)
                }
                item.type == Constants.XIAMI -> {
                    holder.setImageResource(R.id.iv_resource, R.drawable.xiami)
                }
                //如果是视频，跳转到视频播放界面
                item.type == Constants.VIDEO -> {
                    holder.getView<View>(R.id.iv_resource).visibility = View.GONE
                    item.uri?.let {
                        //holder.getView<ImageView>(R.id.iv_cover).setImageBitmap(getVideoThumbnail(it))
                    }
                }
                else -> {
                    holder.getView<View>(R.id.iv_resource).visibility = View.GONE
                }
            }
        }

    }
}