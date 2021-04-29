package com.hongyip.likon.core.model.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout

/**
 * 作者：Ziv_xiao
 * 链接：https://www.jianshu.com/p/a95a1b84da11
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
class AndroidBug5497Workaround {

    companion object {
        fun assistActivity(activity: Activity) {
            AndroidBug5497Workaround(activity)
        }

        fun assistDialog(dialog: Dialog) {
            AndroidBug5497Workaround(dialog)
        }
    }

    private var mChildOfContent: View
    private var usableHeightPrevious = 0
    private var frameLayoutParams: FrameLayout.LayoutParams
    private var contentHeight = 0
    private var isfirst = true
    private var statusBarHeight: Int

    private constructor(activity: Activity) {
        //获取状态栏的高度
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)

        //界面出现变动都会调用这个监听事件
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener {
            if (isfirst) {
                contentHeight = mChildOfContent.height //兼容华为等机型
                isfirst = false
            }
            possiblyResizeChildOfContent()
        }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private constructor(dialog: Dialog) {
        //获取状态栏的高度
        val res = dialog.context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = res.getDimensionPixelSize(resourceId)
        val content = dialog.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)

        //界面出现变动都会调用这个监听事件
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener {
            if (isfirst) {
                contentHeight = mChildOfContent.height //兼容华为等机型
                isfirst = false
            }
            possiblyResizeChildOfContent()
        }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    //重新调整跟布局的高度
    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()

        //当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
            //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight
            } else {
                frameLayoutParams.height = contentHeight
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    /**
     * 计算mChildOfContent可见高度     ** @return
     */
    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

}