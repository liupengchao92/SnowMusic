package com.lpc.snowmusic.ui.mv.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.MvInfoDetail
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.utils.FormatUtil

/**
 * Author: liupengchao
 * Date: 2021/4/8
 * ClassName :MvListAdapter
 * Desc:Mv列表
 */
class MvListAdapter(datas: MutableList<MvInfoDetail>) :

    BaseQuickAdapter<MvInfoDetail, BaseViewHolder>(R.layout.item_mv_list, datas) {

    override fun convert(holder: BaseViewHolder, item: MvInfoDetail) {
        //标题
        holder.setText(R.id.tv_title, item.name)
        //
        if (holder.adapterPosition > 2) {
            holder.setTextColor(R.id.tv_num, Color.WHITE)
        } else {
            holder.setTextColor(R.id.tv_num, Color.RED)
        }
        holder.setText(R.id.tv_num, (holder.adapterPosition + 1).toString())
        //播放次数
        holder.setText(R.id.tv_playCount, FormatUtil.formatPlayCount(item.playCount))
        //加载封面
        GlideUtils.loadImageView(context, item.cover, holder.getView(R.id.iv_cover))
    }
}