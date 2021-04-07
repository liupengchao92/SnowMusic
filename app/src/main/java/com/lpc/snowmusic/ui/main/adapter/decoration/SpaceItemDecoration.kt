package com.lpc.snowmusic.ui.main.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :SpaceItemDecoration
 * Desc:
 */
class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space
        outRect.bottom = space
        outRect.right = space
        /*//由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 3 == 0) {
            outRect.left = SizeUtils.dp2px(10f)
        } else if (parent.getChildLayoutPosition(view) % 3 == 2) {
            outRect.right = SizeUtils.dp2px(10f)
        }*/
    }
}