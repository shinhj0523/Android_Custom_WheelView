package com.example.test

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.RotateAnimation

class SpinnableImageView : AppCompatImageView {
    private var mCurrAngle = 0.0
    private var mPrevAngle = 0.0
    private var mAddAngle = 0.0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {}

    fun performClick(): Boolean {
        return super.performClick()
    }

    fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val centerOfWidth: Float = getWidth() / 2
        val centerOfHeight: Float = getHeight() / 2
        val x = motionEvent.x
        val y = motionEvent.y
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> mCurrAngle = Math.toDegrees(
                Math.atan2(
                    (x - centerOfWidth).toDouble(),
                    (centerOfHeight - y).toDouble()
                )
            )
            MotionEvent.ACTION_MOVE -> {
                mPrevAngle = mCurrAngle
                mCurrAngle = Math.toDegrees(
                    Math.atan2(
                        (x - centerOfWidth).toDouble(),
                        (centerOfHeight - y).toDouble()
                    )
                )
                animate(this, mAddAngle, mAddAngle + mCurrAngle - mPrevAngle)
                mAddAngle += mCurrAngle - mPrevAngle
            }
            MotionEvent.ACTION_UP -> performClick()
        }
        return true
    }

    private fun animate(view: View, fromDegrees: Double, toDegrees: Double) {
        val rotate = RotateAnimation(
            fromDegrees.toFloat(), toDegrees.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 0
        rotate.fillAfter = true
        view.startAnimation(rotate)
    }
}