package com.lpc.snowmusic.ui.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.PersonalizedItem
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.utils.FormatUtil

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :RecommendAdapter
 * Desc:推荐列表
 */
class RecommendAdapter(datas: MutableList<PersonalizedItem>) :

    BaseQuickAdapter<PersonalizedItem, BaseViewHolder>(R.layout.item_discover_recommend, datas) {

    override fun convert(holder: BaseViewHolder, item: PersonalizedItem) {
        //播放次数
        holder.setText(R.id.tv_play_count, FormatUtil.formatPlayCount(item.playCount.toInt()))
        //推荐描述
        holder.setText(R.id.tv_recommend_desc, item.name)
        //封面
        GlideUtils.loadImageView(context, item.picUrl, holder.getView(R.id.iv_cover))
    }
}