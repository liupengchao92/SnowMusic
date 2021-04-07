package com.lpc.snowmusic.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.BannerBean
import com.lpc.snowmusic.imageload.GlideUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * Author: liupengchao
 * Date: 2021/4/6
 * ClassName :MyBannerAdapter
 * Desc:
 */
class MyBannerAdapter(val datas: MutableList<BannerBean>) :
    BannerAdapter<BannerBean, MyBannerAdapter.BannerViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(itemView)
    }

    override fun onBindView(holder: BannerViewHolder, data: BannerBean?, position: Int, size: Int) {
        holder.itemView?.findViewById<TextView>(R.id.banner_title_tv)?.apply {
            text = datas[position].typeTitle
        }
        holder.itemView?.findViewById<ImageView>(R.id.banner_image)?.let {
            //轮播图封面
            GlideUtils.loadImageView(it.context, datas[position].picUrl, it)
        }
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}