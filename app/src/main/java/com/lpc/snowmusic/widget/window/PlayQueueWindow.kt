package com.lpc.snowmusic.widget.window

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lpc.snowmusic.R
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.player.PlayQueueManager
import com.lpc.snowmusic.utils.UIUtils
import razerdp.basepopup.BasePopupWindow

/**
 * Author: liupengchao
 * Date: 2021/5/17
 * ClassName :PlayQueueWindow
 * Desc:播放队列弹窗
 */
class PlayQueueWindow(context: Context) : BasePopupWindow(context) {
    //歌曲列表
    private var musicList: MutableList<Music>? = null
    //歌曲适配器
    private var songAdapter: SongAdapter? = null

    override fun onCreateContentView(): View = createPopupById(R.layout.layout_play_queue_window)

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)

        popupGravity = Gravity.BOTTOM
        //获取歌曲列表
        musicList = PlayManager.getPlayList()
        //Adapter
        songAdapter = SongAdapter(musicList!!)
        //歌曲数量
        contentView.findViewById<TextView>(R.id.countTv).text = "(${musicList?.size})"
        //播放模式
        UIUtils.updatePlayMode(
            contentView.findViewById<ImageView>(R.id.playModeIv),
            contentView.findViewById<TextView>(R.id.playModeDescTv)
        )
        //切换播放模式
        contentView.findViewById<ImageView>(R.id.playModeIv).setOnClickListener {
            //切换播放模式
            PlayQueueManager.updatePlayMode()
            //更新模式
            UIUtils.updatePlayMode(
                contentView.findViewById<ImageView>(R.id.playModeIv),
                contentView.findViewById<TextView>(R.id.playModeDescTv)
            )
        }
        //清空播放队列
        contentView.findViewById<ImageView>(R.id.clearAllIv).setOnClickListener {
            MaterialDialog(context!!).show {
                title(R.string.playlist_queue_clear)
                positiveButton(R.string.sure) {
                    PlayManager.clearPlayQueue()
                    dismiss()
                }
                negativeButton(R.string.cancel)
            }
        }
        //初始化列表
        contentView.findViewById<RecyclerView>(R.id.recyclerView).run {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = songAdapter

            //滚到正在播放的位置
            scrollToPosition(PlayManager.position())
        }
    }

    /**
     * 适配器
     *
     * */
    inner class SongAdapter(datas: MutableList<Music>) :
        BaseQuickAdapter<Music, BaseViewHolder>(R.layout.item_play_queue_window, datas) {

        override fun convert(holder: BaseViewHolder, item: Music) {
            //歌曲名称
            holder.setText(R.id.songNameTv, item.title)
            //歌手
            holder.setText(R.id.singerTv, item.artist)
            //是否正在播放的歌曲
            if (PlayManager.getPlayingMusic()?.mid == item.mid) {
                //修改字体颜色 colorPrimary
                holder.setTextColorRes(R.id.songNameTv, R.color.colorPrimary)
                holder.setTextColorRes(R.id.singerTv, R.color.colorPrimary)
                holder.setBackgroundResource(R.id.line, R.color.colorPrimary)
                //正在播放的Icon
                holder.setGone(R.id.playingIv, false)
            } else {
                //修改字体颜色 colorPrimary
                holder.setTextColorRes(R.id.songNameTv, R.color.common_black_text)
                holder.setTextColorRes(R.id.singerTv, R.color.color_999999)
                holder.setBackgroundResource(R.id.line, R.color.color_999999)
                //正在播放的Icon
                holder.setGone(R.id.playingIv, true)
            }
            //移除歌曲
            holder.getView<ImageView>(R.id.removeSongIv).setOnClickListener {
                //移除歌曲
                PlayManager.removeFromQueue(holder.adapterPosition)
                //刷新列表
                notifyDataSetChanged()
            }
            holder.getView<LinearLayout>(R.id.clickView).setOnClickListener {
                //播放点击的歌曲
                PlayManager.play(holder.adapterPosition)
                //刷新列表
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateShowAnimator(): Animator =
        ObjectAnimator.ofFloat<View>(displayAnimateView, View.TRANSLATION_Y, height.toFloat(), 0f).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
        }

    override fun onCreateDismissAnimator(): Animator =
        ObjectAnimator.ofFloat<View>(displayAnimateView, View.TRANSLATION_Y, 0f, height.toFloat()).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
        }
}