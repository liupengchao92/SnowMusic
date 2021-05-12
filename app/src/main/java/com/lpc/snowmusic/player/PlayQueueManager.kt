package com.lpc.snowmusic.player

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
}