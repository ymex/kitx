package cn.ymex.kitx.snippet.view

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import cn.ymex.kitx.snippet.R


fun Context.dialogPanel(
    @LayoutRes layout: Int,
    afterShow: (dialog: AlertDialog) -> Unit,
    beforeShow: (builder: AlertDialog.Builder) -> Unit = {},
    fullScreen: Boolean = false
): AlertDialog {
    if (this !is Activity) {
        throw IllegalArgumentException("context must is Activity!")
    }
    val builder =
        AlertDialog.Builder(this, if (fullScreen) R.style.dialogPanel else R.style.dialogPanelRound)
            .setView(layout)
    builder.setCancelable(true)
    beforeShow(builder)
    val dialog = builder.create()
    dialog.window?.setDimAmount(0.6f)
    dialog.show()
    if (fullScreen) {
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    } else {
        val sw = getScreenWidth()
//    val width = sw - 64.0f.px
        val width = sw / 5 * 4
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    afterShow(dialog)
    return dialog
}
