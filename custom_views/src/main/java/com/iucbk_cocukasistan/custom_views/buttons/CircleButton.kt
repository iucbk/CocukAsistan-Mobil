package com.iucbk_cocukasistan.custom_views.buttons

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import com.iucbk_cocukasistan.custom_views.R
import kotlin.math.min

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 18.03.2020 - 17:22          │
//└─────────────────────────────┘

class CircleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(
    context,
    attrs,
    defStyle
) {
    private var centerY = 0
    private var centerX = 0
    private var outerRadius = 0
    private var pressedRingRadius = 0
    private var circlePaint: Paint? = null
    private var focusPaint: Paint? = null
    private var animationProgress = 0f
    private var pressedRingWidth = 0
    private var defaultColor: Int = Color.BLACK
    private var pressedColor = 0
    private var pressedAnimator: ObjectAnimator? = null

    private val PRESSED_COLOR_LIGHTUP = 255 / 25
    private val PRESSED_RING_ALPHA = 75
    private val DEFAULT_PRESSED_RING_WIDTH_DIP = 4
    private val ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime

    init {
        init(context, attrs)
    }

    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        circlePaint?.color = if (pressed) pressedColor else defaultColor
        if (pressed) {
            showPressedRing()
        } else {
            hidePressedRing()
        }
    }

    override fun onDraw(canvas: Canvas) {
        focusPaint?.let {
            canvas.drawCircle(
                centerX.toFloat(),
                centerY.toFloat(),
                pressedRingRadius + animationProgress,
                it
            )
        }
        canvas.drawCircle(
            centerX.toFloat(), centerY.toFloat(), (outerRadius - pressedRingWidth).toFloat(),
            this.circlePaint!!
        )
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        outerRadius = min(w, h) / 2
        pressedRingRadius = outerRadius - pressedRingWidth - pressedRingWidth / 2
    }

    fun getAnimationProgress(): Float {
        return animationProgress
    }

    fun setAnimationProgress(animationProgress: Float) {
        this.animationProgress = animationProgress
        this.invalidate()
    }

    private fun setColor(color: Int) {
        defaultColor = color
        pressedColor = getHighlightColor(color)
        circlePaint?.color = defaultColor
        focusPaint?.color = defaultColor
        focusPaint?.alpha = PRESSED_RING_ALPHA
        this.invalidate()
    }

    private fun hidePressedRing() {
        pressedAnimator!!.setFloatValues(pressedRingWidth.toFloat(), 0f)
        pressedAnimator!!.start()
    }

    private fun showPressedRing() {
        pressedAnimator!!.setFloatValues(animationProgress, pressedRingWidth.toFloat())
        pressedAnimator!!.start()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        this.isFocusable = true
        this.scaleType = ScaleType.CENTER_INSIDE
        isClickable = true
        adjustViewBounds = true
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint!!.style = Paint.Style.FILL
        focusPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        focusPaint?.style = Paint.Style.STROKE
        pressedRingWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DEFAULT_PRESSED_RING_WIDTH_DIP.toFloat(),
            resources.displayMetrics
        ).toInt()
        var color: Int = Color.CYAN
        if (attrs != null) {
            val a: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CircleButton
            )
            color = a.getColor(R.styleable.CircleButton_cb_color, color)
            pressedRingWidth = a.getDimension(
                R.styleable.CircleButton_cb_pressedRingWidth,
                pressedRingWidth.toFloat()
            ).toInt()
            a.recycle()
        }
        setColor(color)
        focusPaint!!.strokeWidth = pressedRingWidth.toFloat()
        val pressedAnimationTime =
            resources.getInteger(ANIMATION_TIME_ID)
        pressedAnimator = ObjectAnimator
            .ofFloat(this, "animationProgress", 0f, 0f)
            .setDuration(pressedAnimationTime.toLong())
    }

    private fun getHighlightColor(color: Int): Int {
        return Color.argb(
            min(255, Color.alpha(color)),
            min(255, Color.red(color) + PRESSED_COLOR_LIGHTUP),
            min(255, Color.green(color) + PRESSED_COLOR_LIGHTUP),
            min(255, Color.blue(color) + PRESSED_COLOR_LIGHTUP)
        )
    }
}