package com.lpc.snowmusic.ui.discover.activity

import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ImmersionBar
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.Album
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.bean.Music
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.ArtistDetailContract
import com.lpc.snowmusic.mvp.presenter.ArtistDetailPresenter
import com.lpc.snowmusic.ui.discover.adapter.ArtistFragmentAdapter
import com.lpc.snowmusic.ui.discover.fragment.ArtistAlbumFragment
import com.lpc.snowmusic.ui.discover.fragment.ArtistDetailFragment
import com.lpc.snowmusic.ui.discover.fragment.ArtistSongFragment
import com.lpc.snowmusic.utils.FontUtils
import com.lpc.snowmusic.widget.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_artist_detail.*

class ArtistDetailActivity : BaseMvpActivity<ArtistDetailContract.View, ArtistDetailContract.Presenter>(),
    ArtistDetailContract.View {
    //歌手信息实体
    private lateinit var artist: Artist
    //Tab标题
    private val titles: Array<String>  by lazy {
        resources.getStringArray(R.array.artist_tab_title)
    }
    //歌曲列表
    private val songFragment: ArtistSongFragment = ArtistSongFragment()
    //专辑
    private val albumFragment: ArtistAlbumFragment = ArtistAlbumFragment()
    //歌手详情
    private val detailFragment: ArtistDetailFragment = ArtistDetailFragment()


    override fun createPresenter(): ArtistDetailContract.Presenter = ArtistDetailPresenter()

    override fun getLayoutResId(): Int = R.layout.activity_artist_detail

    override fun initView() {
        super.initView()
        //初始化ViewPager
        viewPager2.run {
            offscreenPageLimit = 2
            adapter = ArtistFragmentAdapter(
                mutableListOf(songFragment, albumFragment, detailFragment),
                this@ArtistDetailActivity
            )
            //设置TabLayout与ViewPager联动
            TabLayoutMediator(tab_layout, viewPager2) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).transparentStatusBar().init()
    }

    override fun initToolBar() {
        super.initToolBar()
        //获取传递的参数
        artist = intent.extras.getParcelable(Extras.ARTIST)
        toolbar.run {
            artist?.let {
                title = it.name
                GlideUtils.loadBigImageView(this@ArtistDetailActivity, it.picUrl, it.type, iv_cover)
            }
            setSupportActionBar(this as Toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            val titleTextView = getChildAt(0) as TextView
            FontUtils.setTypeface(titleTextView)
        }
    }

    override fun start() {
        super.start()
        artist?.let {
            presenter?.loadArtistSongs(it.type!!, it.artistId!!, 50)
        }
    }

    override fun showSongs(songList: MutableList<Music>) {
        songFragment.showSongList(songList)
    }

    override fun showArtistInfo(artist: Artist) {
        detailFragment?.updateArtistDesc(artist)
    }

    override fun showAllAlbum(albumList: MutableList<Album>) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
