package com.lpc.snowmusic.widget

import android.content.Context
import android.util.AttributeSet
import com.makeramen.roundedimageview.RoundedImageView

/**
 * Author: liupengchao
 * Date: 2021/4/7
 * ClassName :RecommendImageView
 * Desc:宽高比为2：3
 */
class RecommendImageView : RoundedImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredWidth * 3 / 2)
    }
}