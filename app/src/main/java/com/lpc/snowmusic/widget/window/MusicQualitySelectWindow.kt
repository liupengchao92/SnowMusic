package com.lpc.snowmusic.widget.window

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.player.PlayManager
import razerdp.basepopup.BasePopupWindow

/**
 * Author: liupengchao
 * Date: 2021/5/17
 * ClassName :MusicQualityWindow
 * Desc:音质选择弹窗
 */
class MusicQualitySelectWindow(context: Context, var music: Music) : BasePopupWindow(context) {
    //选择时间
    var onQualitySelectListener: ((String) -> Unit)? = null

    override fun onCreateContentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_quality_window, null, false)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        //弹窗的位置
        popupGravity = Gravity.BOTTOM

        //音质
        val qualities = mutableListOf(
            QualityItem(name = "标准品质", quality = 128000),
            QualityItem(name = "较高品质", quality = 192000),
            QualityItem(name = "HQ品质", quality = 320000),
            QualityItem(name = "无损品质", quality = 999000)
        )
        //音质列表
        contentView.findViewById<RecyclerView>(R.id.recyclerView).run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = QualityAdapter(qualities)
        }
    }

    /**
     * 适配器
     * */
    inner class QualityAdapter(items: MutableList<QualityItem>) :
        BaseQuickAdapter<QualityItem, BaseViewHolder>(R.layout.item_quality_select, items) {

        override fun convert(holder: BaseViewHolder, item: QualityItem) {
            //设置音质
            holder.setText(R.id.tv_quality, item.name)
            //是否选中
            holder.getView<ImageView>(R.id.iv_check).visibility =
                if (item.quality == music.quality) View.VISIBLE else View.GONE
            //点击事件
            holder.itemView.setOnClickListener {
                music.quality = item.quality
                notifyDataSetChanged()
                onQualitySelectListener?.invoke(item.name)
                PlayManager.play(PlayManager.position())
                dismiss()
            }
        }
    }

}

data class QualityItem(val name: String, val quality: Int)

