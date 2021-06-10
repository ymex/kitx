package cn.ymex.kitx.tips.view

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.*
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

class SpaceItemDecoration(private val space: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.layoutManager) {
            is StaggeredGridLayoutManager -> setOutRectByGrid(outRect, view, parent)
            is GridLayoutManager -> setOutRectByGrid(outRect, view, parent)
            is LinearLayoutManager -> setOutRectByLinear(outRect, view, parent)
            else -> outRect.set(space, space, space, space)
        }
    }

    private fun setOutRectByGrid(rect: Rect, view: View, parent: RecyclerView) {
        //获取当前View的位置
        val position = parent.getChildAdapterPosition(view)
        //计算有多少列
        val columnCount = getSpanCount(parent)
        //当前是第几列
        val column = position % columnCount
        //当前是第几行
        val row = position / columnCount
        //计算平均间隔
        val average = space / columnCount.toFloat()

        rect.left = ((columnCount - column) * average).toInt()
        rect.top = if (row == 0) space else 0
        rect.right = ((column + 1) * average).toInt()
        rect.bottom = space
    }

    private fun setOutRectByLinear(rect: Rect, view: View, parent: RecyclerView) {
        val position = parent.getChildLayoutPosition(view)
        val orientation = getLinearLayoutOrientation(parent)
        val isFirstView = position == 0
        val isHorizontal = orientation == LinearLayoutManager.HORIZONTAL
        val isVertical = orientation == LinearLayoutManager.VERTICAL
        rect.right = if (isVertical) space else space / 2
        rect.bottom = if (isHorizontal) space else space / 2
        rect.left = if (isVertical || isFirstView) space else space / 2
        rect.top = if (isHorizontal || isFirstView) space else space / 2
    }

    private fun getSpanCount(recyclerView: RecyclerView): Int {
        return when (val manager = recyclerView.layoutManager) {
            is StaggeredGridLayoutManager -> manager.spanCount
            is GridLayoutManager -> manager.spanCount
            else -> 1
        }
    }

    private fun getLinearLayoutOrientation(parent: RecyclerView): Int {
        return (parent.layoutManager as LinearLayoutManager).orientation
    }

}