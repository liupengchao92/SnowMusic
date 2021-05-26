package com.lpc.snowmusic.event

import com.lpc.snowmusic.bean.Playlist
import com.lpc.snowmusic.constant.Constants

/**
 * Author: liupengchao
 * Date: 2021/5/26
 * ClassName :PlayListEvent
 * Desc:
 */
 class PlayListEvent(var type: String? = Constants.PLAYLIST_CUSTOM_ID, val playlist: Playlist? = null)