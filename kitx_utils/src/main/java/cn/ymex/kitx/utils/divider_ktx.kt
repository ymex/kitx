package cn.ymex.kitx.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


fun itemDecorationDrawable(
    color: Int = Color.GRAY,
    left: Int = 0,
    right: Int = 0,
    height: Int = 4
): Drawable {
    val drawable = GradientDrawable()
    drawable.setColor(color)
    drawable.setSize(ViewGroup.LayoutParams.MATCH_PARENT, height)
    return InsetDrawable(drawable, left, 0, right, 0)
}


fun verticalItemDecoration(
    context: Context?,
    drawable: Drawable?
): ItemDecoration {
    val itemDecoration =
        DividerItemDecoration(context, LinearLayout.VERTICAL)
    if (drawable != null) {
        itemDecoration.setDrawable(drawable)
    }
    return itemDecoration
}
