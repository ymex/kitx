package cn.ymex.kitx.snippet.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import cn.ymex.kitx.snippet.R


data class DialogPanelAppearance(
    /**
     * 对话框的宽
     */
    val layoutWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    /**
     * 对话框的高
     */
    val layoutHeight: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    /**
     * 对话框遮罩透明度
     */
    val dimAmount: Float = 0.6f,
    /**
     * 对话框的主题样式
     */
    @StyleRes val themeResId: Int = R.style.dialogPanel
)


fun Context.roundDialogPanelAppearance(): DialogPanelAppearance {
    val sw = getScreenWidth()
    val width = sw - 64.dp
    return DialogPanelAppearance(
        width,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        0.6f,
        R.style.dialogPanelRound
    )
}

fun Context.dialogPanel(
    view: View,
    config: DialogPanelAppearance = roundDialogPanelAppearance(),
    builderCall: (builder: AlertDialog.Builder) -> Unit = {},
): AlertDialog {
    return dialogPanel(0, view, config, builderCall)
}

fun Context.dialogPanel(
    @LayoutRes layout: Int,
    config: DialogPanelAppearance = roundDialogPanelAppearance(),
    builderCall: (builder: AlertDialog.Builder) -> Unit = {},
): AlertDialog {
    return dialogPanel(layout, null, config, builderCall)
}


private fun Context.dialogPanel(
    @LayoutRes layout: Int,
    view: View?,
    config: DialogPanelAppearance = roundDialogPanelAppearance(),
    builderCall: (builder: AlertDialog.Builder) -> Unit = {},
): AlertDialog {
    if (this !is Activity) {
        throw IllegalArgumentException("context must is Activity!")
    }
    val builder =
        AlertDialog.Builder(this, config.themeResId)

    if (view != null) {
        builder.setView(view)
    }
    if (layout != 0){
        builder.setView(layout)
    }
    builder.setCancelable(false)
    builderCall(builder)
    val dialog = builder.create()
    dialog.window?.setDimAmount(0.6f)
    dialog.show()
    dialog.window?.setLayout(config.layoutWidth, config.layoutHeight)
    return dialog
}
