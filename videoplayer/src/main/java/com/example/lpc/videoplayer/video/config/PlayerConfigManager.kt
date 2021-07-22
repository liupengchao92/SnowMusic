package com.example.lpc.videoplayer.video.config

import com.example.lpc.videoplayer.video.player.base.AbstractMediaPlayer
import com.example.lpc.videoplayer.video.player.SystemMediaPlayer

/**
 * Author: liupengchao
 * Date: 2021/7/14
 * ClassName :PlayerConfigManger
 * Desc:播放器配置类
 */
object PlayerConfigManager {

    var playerClass: Class<AbstractMediaPlayer>? = null

    fun setPlayerKernel(playerClass: Class<AbstractMediaPlayer>) {
        this.playerClass = playerClass
    }


    fun getPlayer(): AbstractMediaPlayer? {
        if (playerClass == null) {
            playerClass = SystemMediaPlayer::class.java as Class<AbstractMediaPlayer>?
        }
        try {
            return playerClass?.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}