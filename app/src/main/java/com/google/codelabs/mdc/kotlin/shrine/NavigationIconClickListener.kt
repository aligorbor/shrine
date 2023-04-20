package com.google.codelabs.mdc.kotlin.shrine

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView

/**
 * [android.view.View.OnClickListener] used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
class NavigationIconClickListener @JvmOverloads internal constructor(
        private val context: Context, private val sheet: View, private val interpolator: Interpolator? = null,
        private val openIcon: Drawable? = null, private val closeIcon: Drawable? = null) : View.OnClickListener {

    private val animatorSet = AnimatorSet()
    private val height: Int
    private val width: Int
    private var backdropShown = false

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
    }

    override fun onClick(view: View) {
        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        updateIcon(view)

        val translateY = height - context.resources.getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height)
        val translateX = width - context.resources.getDimensionPixelSize(R.dimen.shr_product_grid_reveal_width)

        var animator = ObjectAnimator.ofFloat(sheet, "translationY", (if (backdropShown) translateY else 0).toFloat())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pathTo = Path()
            pathTo.moveTo(translateX.toFloat(),translateY.toFloat())
            pathTo.lineTo(0.toFloat(),0.toFloat())
            val pathFrom= Path()
            pathFrom.moveTo(0.toFloat(),0.toFloat())
            pathFrom.lineTo(translateX.toFloat(),translateY.toFloat())
             animator =  ObjectAnimator.ofFloat(sheet, "translationX", "translationY", if (backdropShown) pathTo else pathFrom)
        }

        animator.duration = 500
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    private fun updateIcon(view: View) {
        if (openIcon != null && closeIcon != null) {
            if (view !is ImageView) {
                throw IllegalArgumentException("updateIcon() must be called on an ImageView")
            }
            if (backdropShown) {
                view.setImageDrawable(closeIcon)
            } else {
                view.setImageDrawable(openIcon)
            }
        }
    }
}
