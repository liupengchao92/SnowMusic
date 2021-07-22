package com.lpc.snowmusic.ui.mv.activity

import android.widget.VideoView
import com.cyl.musicapi.netease.CommentsItemInfo
import com.cyl.musicapi.netease.MvInfoDetail
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.bean.MvInfoDetailInfo
import com.lpc.snowmusic.constant.Extras
import com.lpc.snowmusic.mvp.contract.MvDetailContract
import com.lpc.snowmusic.mvp.presenter.MvDetailPresenter
import kotlinx.android.synthetic.main.activity_mv_detail.*

/**
 * Author: liupengchao
 * Date: 2021/7/7
 * ClassName :MvDetailActivity
 * Desc:MV详情
 */
class MvDetailActivity : BaseMvpActivity<MvDetailContract.View, MvDetailContract.Presenter>(),
    MvDetailContract.View {

    private val videoUrl = "http://vjs.zencdn.net/v/oceans.mp4"

    override fun createPresenter(): MvDetailContract.Presenter = MvDetailPresenter()

    override fun getLayoutResId(): Int = R.layout.activity_mv_detail

    override fun initView() {
        super.initView()
        videoView.setDataSource(videoUrl)

    }


    override fun start() {
        super.start()
        val mvId = intent.getStringExtra(Extras.MV_ID)
        presenter?.loadMvDetail(mvId)
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
    }


    override fun showMvDetailInfo(mvInfoDetailInfo: MvInfoDetailInfo) {
        mvInfoDetailInfo?.let {


            // GlideUtils.loadImageView(it.)
        }

    }

    override fun showMvList(mvList: List<MvInfoDetail?>?) {
    }

    override fun showMvComment(mvCommentInfo: List<CommentsItemInfo?>?) {

    }


}