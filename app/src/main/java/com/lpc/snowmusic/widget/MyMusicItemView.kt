package com.lpc.snowmusic.widget

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lpc.snowmusic.R
import kotlinx.android.synthetic.main.local_item_view_layout.view.*

/**
 * Author: liupengchao
 * Date: 2020/5/14
 * ClassName :LocalItemView
 * Desc:
 */
class MyMusicItemView(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {


    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.MyMusicItemView)
        val name = ta.getString(R.styleable.MyMusicItemView_item_name)
        val desc = ta.getString(R.styleable.MyMusicItemView_item_desc)
        val drawable = ta.getDrawable(R.styleable.MyMusicItemView_item_icon)
        val colorTintList = ta.getColorStateList(R.styleable.MyMusicItemView_item_icon_color)
        ta.recycle()

        val itemView = LayoutInflater.from(context).inflate(R.layout.local_item_view_layout, this, true)
        val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
        val tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)
        val iv_icon = itemView.findViewById<ImageView>(R.id.iv_icon)

        tv_name.text = name
        tv_desc.text = desc
        iv_icon.setImageDrawable(drawable)
        iv_icon.imageTintList = colorTintList
        iv_icon.imageTintMode = PorterDuff.Mode.SRC_ATOP
    }

    fun setItemDesc(desc: String) {
        tv_desc.text = desc
    }

}