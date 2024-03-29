package com.lpc.snowmusic.ui.my.activity

import android.Manifest
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.lpc.snowmusic.R
import com.lpc.snowmusic.base.BaseMvpActivity
import com.lpc.snowmusic.database.loader.PlayLocalLoader
import com.lpc.snowmusic.mvp.contract.PlayLocalContract
import com.lpc.snowmusic.mvp.presenter.PlayLocalPresenter
import com.lpc.snowmusic.ui.my.adapter.LocalPagerAdapter
import com.lpc.snowmusic.utils.ViewPager2Helper
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_play_local.*
import kotlinx.coroutines.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


class PlayLocalActivity : BaseMvpActivity<PlayLocalContract.View, PlayLocalContract.Presenter>() {
    //标题
    private val titles: Array<String> by lazy { getStringArray(R.array.local_tab_title) }

    //是否获取了权限
    private var allGranted: Boolean = false

    //ViewPager适配器
    private val pagerAdapter by lazy { LocalPagerAdapter(this@PlayLocalActivity, titles) }

    override fun getLayoutResId(): Int = R.layout.activity_play_local

    override fun createPresenter(): PlayLocalContract.Presenter = PlayLocalPresenter()

    override fun initImmersionBar() {

    }

    override fun initToolBar() {
        toolBar.title = getString(R.string.item_local)
        super.initToolBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_local_music, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun initView() {
        super.initView()
        //请求权限
        requestPermission()
        //初始化指示器
        val navigator: CommonNavigator = CommonNavigator(this)
        navigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int = titles.size

            override fun getTitleView(context: Context?, position: Int): IPagerTitleView {
                val titleView = SimplePagerTitleView(context)
                titleView.normalColor =
                    ContextCompat.getColor(context!!, R.color.common_black_text)
                titleView.selectedColor =
                    ContextCompat.getColor(context!!, R.color.colorPrimary)
                titleView.textSize = 15.0f
                titleView.text = titles[position]
                titleView.setOnClickListener {
                    viewPager2.setCurrentItem(position, false)
                }
                return titleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = UIUtil.dip2px(context, 45.0)
                val lineHeight = UIUtil.dip2px(context, 26.0).toFloat()
                indicator.lineHeight = lineHeight
                indicator.roundRadius = (lineHeight / 2).toFloat()
                indicator.yOffset = (navigatorHeight - lineHeight) / 2
                indicator.xOffset = UIUtil.dip2px(context, 10.0).toFloat()
                indicator.setColors(ContextCompat.getColor(context!!, R.color.color_1ad81b60))
                indicator.mode = MODE_MATCH_EDGE
                return indicator
            }
        }

        navigator.isAdjustMode = true
        //设置给magicIndicator
        magicIndicator.navigator = navigator

        viewPager2.run {
            adapter = pagerAdapter
        }
        //magicIndicator与viewPager2 绑定
        ViewPager2Helper.bind(magicIndicator, viewPager2)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scan_music -> {
                ToastUtils.showShort("扫描本地音乐")
                if (this.allGranted) {
                    GlobalScope.launch {

                        val result = GlobalScope.async {
                            PlayLocalLoader.getLocalMusic(this@PlayLocalActivity, true)
                        }
                        result.await()
                        withContext(Dispatchers.Main) {
                            //重新加载数据
                            pagerAdapter.reloadLocal(viewPager2.currentItem)
                        }
                    }

                } else {
                    requestPermission()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 请求权限
     *
     * */
    private fun requestPermission() {
        //权限检测
        PermissionX.init(this as FragmentActivity)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    this.allGranted = allGranted
                } else {
                    ToastUtils.showShort("您拒绝了如下权限：$deniedList")
                }
            }
    }

}