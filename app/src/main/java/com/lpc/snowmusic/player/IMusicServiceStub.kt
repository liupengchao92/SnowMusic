package com.lpc.snowmusic.player

import com.lpc.snowmusic.IMusicService
import com.lpc.snowmusic.bean.Music
import java.lang.ref.WeakReference


/**
 * Author: liupengchao
 * Date: 2020/5/31
 * ClassName :IMusicServiceStub
 * Desc:
 */
class IMusicServiceStub(musicPlayerService: MusicPlayerService) : IMusicService.Stub() {
    //
    private val mService: WeakReference<MusicPlayerService> by lazy {
        WeakReference<MusicPlayerService>(musicPlayerService)
    }

    override fun nextPlay(music: Music?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playMusic(music: Music?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playPlaylist(playlist: MutableList<Music>?, id: Int, pid: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun play(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prev() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun next() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoopMode(loopmode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seekTo(ms: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun position(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDuration(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentPosition(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPlaying(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPause(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSongName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSongArtist(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayingMusic(): Music {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayList(): MutableList<Music> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromQueue(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearQueue() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDesktopLyric(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun AudioSessionId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}