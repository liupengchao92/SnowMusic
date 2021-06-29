package com.lpc.snowmusic.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.player.PlayManager
import com.lpc.snowmusic.ui.discover.PlayerActivity
import com.lpc.snowmusic.ui.discover.activity.ArtistDetailActivity
import com.lpc.snowmusic.ui.discover.activity.SongListDetailActivity
import com.lpc.snowmusic.ui.main.activity.MainActivity

/**
 * Author: liupengchao
 * Date: 2021/4/9
 * ClassName :NavigationHelper
 * Desc:
 */
object NavigationHelper {

    fun navigateToMain(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     *跳转歌手详情
     * */
    fun navigateToArtistDetail(context: Context, artist: Artist) {
        val intent = Intent(context, ArtistDetailActivity::class.java)
        intent.putExtra(Extras.ARTIST, artist)
        context.startActivity(intent)
    }

    /**
     *跳转歌单详情
     * */
    fun navigateToSongDetail(context: Context, playlist: Playlist) {
        val intent = Intent(context, SongListDetailActivity::class.java)
        intent.putExtra(Extras.SONG_LIST, playlist)
        context.startActivity(intent)
    }

    /**
     *跳转播放详情页面
     * */
    fun navigateToPlaying(context: Context, transitionView: View? = null) {
        val intent = Intent(context, PlayerActivity::class.java)
        if (transitionView != null) {
            val compat = ActivityOptionsCompat.makeScaleUpAnimation(
                transitionView,
                transitionView.width / 2, transitionView.height / 2, 0, 0
            )
            ActivityCompat.startActivity(context, intent, compat.toBundle())
        } else {
            context.startActivity(intent)
        }
    }

    /**
     *音效
     * */
    fun navigateToSoundEffect(context: Activity) {
        try {
            val effects = Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL)
            effects.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, PlayManager.getAudioSessionId())
            context.startActivityForResult(effects, 666)
        } catch (e: Exception) {
            ToastUtils.showShort("设备不支持均衡！")
        }
    }
}