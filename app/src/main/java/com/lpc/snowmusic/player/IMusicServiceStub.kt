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
    //弱引用MusicPlayerService
    private val mService: WeakReference<MusicPlayerService> by lazy {
        WeakReference<MusicPlayerService>(musicPlayerService)
    }

    override fun nextPlay(music: Music?) {

    }

    override fun playMusic(music: Music?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playPlaylist(playlist: MutableList<Music>?, id: Int, pid: String?) {
        mService.get()?.play(playlist!!, id, pid!!)
    }

    override fun play(id: Int) {
        mService.get()?.play(id)
    }

    override fun playPause() {
        mService.get()?.playPause()
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prev() {
        mService.get()?.prev()
    }

    override fun next() {
        mService.get()?.next(false)
    }

    override fun setLoopMode(loopmode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seekTo(ms: Int) {
        mService.get()?.seekTo(ms)
    }

    override fun position(): Int = mService.get()?.playingPos as Int

    override fun getDuration(): Int = mService.get()?.getDuration()!!.toInt()

    override fun getCurrentPosition(): Int = mService.get()?.getCurrentPosition()!!.toInt()

    override fun isPlaying(): Boolean = mService.get()?.isMusicPlaying!!

    override fun isPause(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSongName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSongArtist(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 获取正在播放的音乐
     * */
    override fun getPlayingMusic(): Music? = mService.get()?.playingMusic

    override fun getPlayList(): MutableList<Music> = mService.get()?.playQueue!!

    override fun removeFromQueue(position: Int) {
        mService.get()?.removeFromQueue(position)
    }

    override fun clearQueue() {
        mService.get()?.clearQueue()
    }

    override fun showDesktopLyric(show: Boolean) {
        mService.get()?.showDesktopLyric(show)
    }

    override fun AudioSessionId(): Int = mService.get()?.getAudioSessionId()!!
}