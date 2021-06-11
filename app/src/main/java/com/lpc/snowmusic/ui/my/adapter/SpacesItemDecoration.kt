package com.lpc.snowmusic.ui.my.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: liupengchao
 * Date: 2021/6/10
 * ClassName :SpacesItemDecoration
 * Desc:
 */
class SpacesItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager is GridLayoutManager) {
            outRect.top = space
            outRect.right = space
        }
    }
}