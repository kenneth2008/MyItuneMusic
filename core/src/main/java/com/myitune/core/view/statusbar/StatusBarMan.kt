package com.myitune.core.view.statusbar

import android.app.Activity
import android.view.Window
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

class StatusBarMan(
        private val act: Activity? = null
) {

    fun statusBarColorMode(@ColorRes color: Int) {
        if (act != null) {
            if (ColorUtils.calculateLuminance(ContextCompat.getColor(act, color)) > 0.5)
                OldStatusBarUtil.StatusBarLightMode(act)
            else
                OldStatusBarUtil.StatusBarDarkMode(act)

            OldStatusBarUtil.setStatusBarColor(act, color)
        }
    }

    fun statusBarLightTransparentMode() {
        if (act != null) {
            OldStatusBarUtil.StatusBarLightTransparentMode(act.window)
        }
    }

    fun statusBarLightTransparentMode(window: Window) {
//        StatusBarUtil.setNavigationBarColor(window)
//        StatusBarUtil.setTransparentForWindow(window)
//        StatusBarUtil.setDarkMode(window)

        OldStatusBarUtil.StatusBarLightTransparentMode(window)
    }

    fun statusBarDarkTransparentMode() {
        if (act != null) {
            OldStatusBarUtil.StatusBarDarkTransparentMode(act.window)
        }
    }

    fun statusBarDarkTransparentMode(window: Window) {
//        StatusBarUtil.setNavigationBarColor(window)
//        StatusBarUtil.setTransparentForWindow(window)
//        StatusBarUtil.setLightMode(window)

        OldStatusBarUtil.StatusBarDarkTransparentMode(window)
    }

}