package com.lpc.snowmusic.player

import com.lpc.snowmusic.R
import com.lpc.snowmusic.application.MusicApplication
import com.lpc.snowmusic.constant.SPkeyConstant
import com.lpc.snowmusic.event.PlayModeEvent
import com.lpc.snowmusic.utils.MMKVUtils
import org.greenrobot.eventbus.EventBus

/**
 * Author: liupengchao
 * Date: 2021/5/8
 * ClassName :PlayQueueManager
 * Desc:播放队列的管理
 * */
object PlayQueueManager {
    //顺序播放
    const val PLAY_MODE_LOOP = 0
    //单曲循环
    const val PLAY_MODE_REPEAT = 1
    //随机播放
    const val PLAY_MODE_RANDOM = 2
    //当前的播放模式
    var playingModeId: Int = PLAY_MODE_LOOP
    //播放模式
    val playMode = MusicApplication.context.resources.getStringArray(R.array.play_mode)

    /**
     * 更新播放模式
     *
     * */
    fun updatePlayMode(): Int {
        playingModeId = (playingModeId + 1) % 3
        MMKVUtils.putValue(SPkeyConstant.PLAY_MODE, playingModeId)
        EventBus.getDefault().post(PlayModeEvent(playingModeId))
        return playingModeId
    }

    /**
     * 获取播放模式id
     */
    fun getPlayModeId(): Int {
        playingModeId = MMKVUtils.getInt(SPkeyConstant.PLAY_MODE)!!
        return playingModeId
    }

    /**
     * 获取播放模式
     */
    fun getPlayMode(): String = playMode[playingModeId]

    /**
     * 获取下一首位置
     *
     * @return isAuto 是否自动下一曲
     */
    fun getNextPosition(isAuto: Boolean = false, total: Int, current: Int): Int {
        if (total == 1) return 0
        if (playingModeId == PLAY_MODE_REPEAT && isAuto) {
            return if (current < 0) 0 else current

        } else if (playingModeId == PLAY_MODE_RANDOM) {
            return java.util.Random().nextInt(total)

        } else {
            if (current >= total - 1) {
                return 0
            } else if (current < total) {
                return current + 1
            }
        }
        return current
    }

    /**
     * 获取前一首歌曲
     *
     * @return isAuto 是否自动下一曲
     */
    fun getPreviousPosition(total: Int, current: Int): Int {
        if (total == 1) return 0
        if (playingModeId == PLAY_MODE_REPEAT) {
            return if (current < 0) 0 else current

        } else if (playingModeId == PLAY_MODE_RANDOM) {
            return java.util.Random().nextInt(total)

        } else {
            if (current == 0) {
                return total - 1
            } else if (current > 0) {
                return current - 1
            }
        }
        return current
    }
}