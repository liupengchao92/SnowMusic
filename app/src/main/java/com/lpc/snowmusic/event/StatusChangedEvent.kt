package com.lpc.snowmusic.event

/**
 * Author: liupengchao
 * Date: 2021/5/8
 * ClassName :StatusChangedEvent
 * Desc:状态变更
 */
class StatusChangedEvent(var isPrepared: Boolean, var isPlaying: Boolean, var percent: Long = 0)