package com.jc.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Range
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author JQChen.
 * @date on 11/29/2021.
 */
class ClickableIconEditText(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatEditText(context!!, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.editTextStyle
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_UP) return super.onTouchEvent(event)
        if (onIconClickListener == null) return super.onTouchEvent(event)
        val drawables: Array<Drawable?> = compoundDrawables
        //0左边图片 1 上边图片 2右边图片 3 下边图片
        val dl: Drawable? = drawables[CLICK_LEFT_ICON]
        val dt: Drawable? = drawables[CLICK_TOP_ICON]
        val dr: Drawable? = drawables[CLICK_RIGHT_ICON]
        val db: Drawable? = drawables[CLICK_BOTTOM_ICON]
        val pt = paddingTop + (dt?.intrinsicHeight ?: 0)
        val pb = paddingBottom + (db?.intrinsicHeight ?: 0)
        val pl = paddingLeft + (dl?.intrinsicWidth ?: 0)
        val pr = paddingRight + (dr?.intrinsicWidth ?: 0)
        val x: Float = event.x
        val y: Float = event.y
        val mx = (width - pl - pr) / 2 + pl
        val my = (height - pt - pb) / 2 + pt
        if (dl != null) {
            val dlw: Int = dl.intrinsicWidth
            val dlh: Int = dl.intrinsicHeight
            val rlx: Range<Int> = Range(
                paddingLeft,
                paddingLeft + dlw
            )
            val rly: Range<Int> = Range(
                my - dlh / 2,
                my + dlh / 2
            )
            if (rlx.contains(x.toInt()) && rly.contains(y.toInt())) {
                onIconClickListener?.onIconClickListener(this, CLICK_LEFT_ICON)
                return true
            }
        }
        if (dr != null) {
            val drw: Int = dr.intrinsicWidth
            val drh: Int = dr.intrinsicHeight
            val rrx: Range<Int> = Range(
                width - paddingRight - drw,
                width - paddingRight
            )
            val rry: Range<Int> = Range(
                my - drh / 2,
                my + drh / 2 + drw
            )
            if (rrx.contains(x.toInt()) && rry.contains(y.toInt())) {
                onIconClickListener?.onIconClickListener(this, CLICK_RIGHT_ICON)
                return true
            }
        }
        if (dt != null) {
            val dtw: Int = dt.intrinsicWidth
            val rtx: Range<Int> = Range(
                mx - dtw / 2,
                mx + dtw / 2
            )
            val rty: Range<Int> = Range(paddingTop, pt)
            if (rtx.contains(x.toInt()) && rty.contains(y.toInt())) {
                onIconClickListener?.onIconClickListener(this, CLICK_TOP_ICON)
                return true
            }
        }
        if (db != null) {
            val dbw: Int = db.intrinsicWidth
            val rbx: Range<Int> = Range(
                mx - dbw / 2,
                mx + dbw / 2
            )
            val rby: Range<Int> = Range(
                height - pb,
                height - paddingBottom
            )
            if (rbx.contains(x.toInt()) && rby.contains(y.toInt())) {
                onIconClickListener?.onIconClickListener(this, CLICK_BOTTOM_ICON)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private var onIconClickListener: OnIconClickListener? = null

    fun setOnIconClickListener(onIconClickListener: OnIconClickListener?) {
        this.onIconClickListener = onIconClickListener
    }

    interface OnIconClickListener {
        /**
         * @param view 被点击的view
         * @param iconType 点击的图标位置 0：左边 1:上边 2:右边 3:下边
         */
        fun onIconClickListener(view: ClickableIconEditText?, iconType: Int)
    }

    companion object {
        const val CLICK_LEFT_ICON = 0
        const val CLICK_TOP_ICON = 1
        const val CLICK_RIGHT_ICON = 2
        const val CLICK_BOTTOM_ICON = 3
    }
}