package com.lpc.snowmusic.ui.discover.activity

import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.Artist
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.imageload.GlideUtils
import com.lpc.snowmusic.mvp.contract.ArtistDetailContract
import com.lpc.snowmusic.mvp.presenter.ArtistDetailPresenter
import kotlinx.android.synthetic.main.activity_artist_detail.*

class ArtistDetailActivity : BaseMvpActivity<ArtistDetailContract.View, ArtistDetailContract.Presenter>() {
    //歌手信息实体
    private lateinit var artist: Artist

    override fun createPresenter(): ArtistDetailContract.Presenter = ArtistDetailPresenter()

    override fun getLayoutResId(): Int = R.layout.activity_artist_detail

    override fun initView() {
        super.initView()

    }

    override fun initToolBar() {
        super.initToolBar()
        //获取传递的参数
        artist = intent.extras.getParcelable(Extras.ARTIST)
        artist?.let {
            title = it.name
            GlideUtils.loadBigImageView(this, it.picUrl, it.type, iv_cover)
        }
    }

}
