package com.example.androidpracticumcustomview.ui.theme

import android.animation.AnimatorSet
import android.animation.ObjectAnimator.ofFloat
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount > 0 && changed) {
            val child = getChildAt(0)
            val childLeft = (right - left - child.measuredWidth) / 2
            val childTop = (bottom / 4) // 1/4 высоты ViewGroup
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
            startAnimation(true, child)
        }
        if (childCount > 1) {
            val child = getChildAt(1)
            val childLeft = (right - left - child.measuredWidth) / 2
            val childTop = (bottom / 4) * 3 // 3/4 высоты ViewGroup
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
            startAnimation(false, child)
        }
    }

    private fun startAnimation(isFirst: Boolean, view: View) {
        val animTranslation = if (isFirst) {
            ofFloat(
                view, "translationY", height / 4f - view.height, 0f
            )
        } else {
            ofFloat(
                view, "translationY", view.height - height / 4f, 0f
            )
        }
        animTranslation.duration = 5000

        val animAlpha = ofFloat(view, "alpha", 0f, 1f)
        animAlpha.duration = 2000

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animTranslation, animAlpha)
        animatorSet.start()
   }


    override fun addView(child: View) {
        if (childCount > 2) {
            throw IllegalStateException("Дочерних элементов не может быть больше двух")
        }
        super.addView(child)
    }
}