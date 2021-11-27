package com.tmdb.utils.image_motion

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.appcompat.widget.AppCompatImageView
import com.tmdb.utils.image_motion.MathUtils.getRectRatio
import com.tmdb.utils.image_motion.MathUtils.truncate
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

class MotionImageView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : AppCompatImageView(context!!, attrs, defStyle) {
    private val mMatrix = Matrix()

    private var mTransGen: TransitionGenerator = RandomTransitionGenerator()

    private var mTransitionListener: TransitionListener? = null

    private var mCurrentTrans: Transition? = null

    private val mViewportRect = RectF()

    private var mDrawableRect: RectF? = null

    private var mElapsedTime: Long = 0

    private var mLastFrameTime: Long = 0

    private var mPaused = false

    private val mInitialized = true
    override fun setScaleType(scaleType: ScaleType) {
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (visibility) {
            View.VISIBLE -> resume()
            else -> pause()
        }
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        handleImageChange()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        handleImageChange()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        handleImageChange()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        handleImageChange()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        restart()
    }

    override fun onDraw(canvas: Canvas) {
        val d = drawable
        if (!mPaused && d != null) {
            if (mDrawableRect!!.isEmpty) {
                updateDrawableBounds()
            } else if (hasBounds()) {
                if (mCurrentTrans == null) { // Starting the first transition.
                    startNewTransition()
                }
                // If null, it's supposed to stop.
                mElapsedTime += System.currentTimeMillis() - mLastFrameTime
                val currentRect: RectF = mCurrentTrans!!.getInterpolatedRect(mElapsedTime)
                val widthScale = mDrawableRect!!.width() / currentRect.width()
                val heightScale = mDrawableRect!!.height() / currentRect.height()
                // Scale to make the current rect match the smallest drawable dimension.
                val currRectToDrwScale = widthScale.coerceAtMost(heightScale)
                // Scale to make the current rect match the viewport bounds.
                val currRectToVpScale = mViewportRect.width() / currentRect.width()
                // Combines the two scales to fill the viewport with the current rect.
                val totalScale = currRectToDrwScale * currRectToVpScale
                val translX = totalScale * (mDrawableRect!!.centerX() - currentRect.left)
                val translY = totalScale * (mDrawableRect!!.centerY() - currentRect.top)

                mMatrix.reset()
                mMatrix.postTranslate(-mDrawableRect!!.width() / 2, -mDrawableRect!!.height() / 2)
                mMatrix.postScale(totalScale, totalScale)
                mMatrix.postTranslate(translX, translY)
                imageMatrix = mMatrix

                if (mElapsedTime >= mCurrentTrans!!.duration) {
                    fireTransitionEnd(mCurrentTrans)
                    startNewTransition()
                }
            }
            mLastFrameTime = System.currentTimeMillis()
            postInvalidateDelayed(FRAME_DELAY)
        }
        super.onDraw(canvas)
    }

    private fun startNewTransition() {
        if (!hasBounds()) {
            throw UnsupportedOperationException(
                "Can't start transition if the " +
                        "drawable has no bounds!"
            )
        }
        mCurrentTrans = mTransGen.generateNextTransition(mDrawableRect, mViewportRect)
        mElapsedTime = 0
        mLastFrameTime = System.currentTimeMillis()
        fireTransitionStart(mCurrentTrans)
    }

    private fun restart() {
        val width = width
        val height = height
        if (width == 0 || height == 0) {
            throw UnsupportedOperationException("Can't call restart() when view area is zero!")
        }
        updateViewport(width.toFloat(), height.toFloat())
        updateDrawableBounds()
        if (hasBounds()) {
            startNewTransition()
        }
    }

    private fun hasBounds(): Boolean {
        return !mViewportRect.isEmpty
    }

    private fun fireTransitionStart(transition: Transition?) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener!!.onTransitionStart(transition)
        }
    }

    private fun fireTransitionEnd(transition: Transition?) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener!!.onTransitionEnd(transition)
        }
    }

    fun setTransitionGenerator(transGen: TransitionGenerator) {
        mTransGen = transGen
        if (hasBounds()) {
            startNewTransition()
        }
    }

    private fun updateViewport(width: Float, height: Float) {
        mViewportRect[0f, 0f, width] = height
    }

    private fun updateDrawableBounds() {
        if (mDrawableRect == null) {
            mDrawableRect = RectF()
        }
        val d = drawable
        if (d != null) {
            mDrawableRect!![0f, 0f, d.intrinsicWidth.toFloat()] = d.intrinsicHeight.toFloat()
        }
    }

    private fun handleImageChange() {
        updateDrawableBounds()

        if (mInitialized && hasBounds()) {
            startNewTransition()
        }
    }

    fun setTransitionListener(transitionListener: TransitionListener?) {
        mTransitionListener = transitionListener
    }

    private fun pause() {
        mPaused = true
    }

    private fun resume() {
        mPaused = false
        // This will make the animation to continue from where it stopped.
        mLastFrameTime = System.currentTimeMillis()
        invalidate()
    }

    interface TransitionListener {
        /**
         * Notifies the start of a transition.
         *
         * @param transition the transition that just started.
         */
        fun onTransitionStart(transition: Transition?)

        /**
         * Notifies the end of a transition.
         *
         * @param transition the transition that just ended.
         */
        fun onTransitionEnd(transition: Transition?)
    }

    companion object {
        /**
         * Delay between a pair of frames at a 60 FPS frame rate.
         */
        private const val FRAME_DELAY = 1000 / 60.toLong()
    }

    init {
        // Attention to the super call here!
        super.setScaleType(ScaleType.MATRIX)
    }
}

object MathUtils {
    fun truncate(f: Float, decimalPlaces: Int): Float {
        val decimalShift = 10.0.pow(decimalPlaces.toDouble()).toFloat()
        return (f * decimalShift).roundToInt() / decimalShift
    }

    internal fun haveSameAspectRatio(r1: RectF, r2: RectF): Boolean {
        val srcRectRatio = truncate(getRectRatio(r1), 2)
        val dstRectRatio = truncate(getRectRatio(r2), 2)

        return abs(srcRectRatio - dstRectRatio) <= 0.01f
    }

    fun getRectRatio(rect: RectF): Float {
        return rect.width() / rect.height()
    }
}

class RandomTransitionGenerator @JvmOverloads constructor(
    transitionDuration: Long = DEFAULT_TRANSITION_DURATION.toLong(),
    transitionInterpolator: Interpolator? = AccelerateDecelerateInterpolator()
) :
    TransitionGenerator {
    private val mRandom: Random = Random(System.currentTimeMillis())

    private var mTransitionDuration: Long = 0

    private var mTransitionInterpolator: Interpolator? = null

    private var mLastGenTrans: Transition? = null

    private var mLastDrawableBounds: RectF? = null

    private fun generateRandomRect(drawableBounds: RectF, viewportRect: RectF): RectF {
        val drawableRatio = getRectRatio(drawableBounds)
        val viewportRectRatio = getRectRatio(viewportRect)
        val maxCrop: RectF = if (drawableRatio > viewportRectRatio) {
            val r = drawableBounds.height() / viewportRect.height() * viewportRect.width()
            val b = drawableBounds.height()
            RectF(0F, 0F, r, b)
        } else {
            val r = drawableBounds.width()
            val b = drawableBounds.width() / viewportRect.width() * viewportRect.height()
            RectF(0F, 0F, r, b)
        }
        val randomFloat = truncate(mRandom.nextFloat(), 2)
        val factor = MIN_RECT_FACTOR + (1 - MIN_RECT_FACTOR) * randomFloat
        val width = factor * maxCrop.width()
        val height = factor * maxCrop.height()
        val widthDiff = (drawableBounds.width() - width).toInt()
        val heightDiff = (drawableBounds.height() - height).toInt()
        val left = if (widthDiff > 0) mRandom.nextInt(widthDiff) else 0
        val top = if (heightDiff > 0) mRandom.nextInt(heightDiff) else 0
        return RectF(left.toFloat(), top.toFloat(), left + width, top + height)
    }

    private fun setTransitionDuration(transitionDuration: Long) {
        mTransitionDuration = transitionDuration
    }

    private fun setTransitionInterpolator(interpolator: Interpolator?) {
        mTransitionInterpolator = interpolator
    }

    companion object {
        /** Default value for the transition duration in milliseconds.  */
        const val DEFAULT_TRANSITION_DURATION = 10000

        /** Minimum rect dimension factor, according to the maximum one.  */
        private const val MIN_RECT_FACTOR = 0.75f
    }

    init {
        setTransitionDuration(transitionDuration)
        setTransitionInterpolator(transitionInterpolator)
    }

    override fun generateNextTransition(drawableBounds: RectF?, viewport: RectF?): Transition? {
        val firstTransition = mLastGenTrans == null
        var drawableBoundsChanged = true
        var viewportRatioChanged = true
        val srcRect: RectF?
        var dstRect: RectF? = null
        if (!firstTransition) {
            dstRect = mLastGenTrans?.destinyRect
            drawableBoundsChanged = drawableBounds != mLastDrawableBounds
            viewportRatioChanged = !viewport?.let { MathUtils.haveSameAspectRatio(dstRect!!, it) }!!
        }
        srcRect = if (dstRect == null || drawableBoundsChanged || viewportRatioChanged) {
            generateRandomRect(drawableBounds!!, viewport!!)
        } else {
            dstRect
        }
        dstRect = generateRandomRect(drawableBounds!!, viewport!!)
        mLastGenTrans = Transition(
            srcRect, dstRect, mTransitionDuration,
            mTransitionInterpolator!!
        )
        mLastDrawableBounds = drawableBounds
        return mLastGenTrans
    }
}

class Transition(srcRect: RectF, dstRect: RectF, duration: Long, interpolator: Interpolator) {
    /**
     * Gets the rect that will take the scene when a Ken Burns transition starts.
     *
     * @return the rect that starts the transition.
     */
    /**
     * The rect the transition will start from.
     */
    private val sourceRect: RectF
    val destinyRect: RectF
    private val mCurrentRect = RectF()
    private val mWidthDiff: Float
    private val mHeightDiff: Float
    private val mCenterXDiff: Float
    private val mCenterYDiff: Float

    /**
     * The duration of the transition in milliseconds. The default duration is 5000 ms.
     */
    val duration: Long

    /**
     * The [Interpolator] used to perform the transitions between rects.
     */
    private val mInterpolator: Interpolator

    /**
     * Gets the current rect that represents the part of the image to take the scene
     * in the current frame.
     *
     * @param elapsedTime the elapsed time since this transition started.
     */
    fun getInterpolatedRect(elapsedTime: Long): RectF {
        val elapsedTimeFraction = elapsedTime / duration.toFloat()
        val interpolationProgress = elapsedTimeFraction.coerceAtMost(1f)
        val interpolation = mInterpolator.getInterpolation(interpolationProgress)
        val currentWidth = sourceRect.width() + interpolation * mWidthDiff
        val currentHeight = sourceRect.height() + interpolation * mHeightDiff
        val currentCenterX = sourceRect.centerX() + interpolation * mCenterXDiff
        val currentCenterY = sourceRect.centerY() + interpolation * mCenterYDiff
        val left = currentCenterX - currentWidth / 2
        val top = currentCenterY - currentHeight / 2
        val right = left + currentWidth
        val bottom = top + currentHeight
        mCurrentRect[left, top, right] = bottom
        return mCurrentRect
    }

    init {
        if (!MathUtils.haveSameAspectRatio(srcRect, dstRect)) {
            throw RuntimeException()
        }
        sourceRect = srcRect
        destinyRect = dstRect
        this.duration = duration
        mInterpolator = interpolator

// Precomputes a few variables to avoid doing it in onDraw().
        mWidthDiff = dstRect.width() - srcRect.width()
        mHeightDiff = dstRect.height() - srcRect.height()
        mCenterXDiff = dstRect.centerX() - srcRect.centerX()
        mCenterYDiff = dstRect.centerY() - srcRect.centerY()
    }
}

interface TransitionGenerator {
    fun generateNextTransition(drawableBounds: RectF?, viewport: RectF?): Transition?
}