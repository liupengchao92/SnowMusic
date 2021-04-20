package com.lpc.snowmusic.ui.discover.listener

import com.google.android.material.appbar.AppBarLayout

/**
 * Author: liupengchao
 * Date: 2021/4/20
 * ClassName :AppBarStateChangeListener
 * Desc:
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    //当前状态
    private var currentState: State = State.IDLE

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

        if (verticalOffset == 0) {
            if (currentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, verticalOffset);
            }
            currentState = State.EXPANDED
        } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (currentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, verticalOffset)
            }
            currentState = State.COLLAPSED
        } else {
            if (currentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, verticalOffset)
            }
            currentState = State.IDLE
        }

    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State, verticalOffset: Int)

}