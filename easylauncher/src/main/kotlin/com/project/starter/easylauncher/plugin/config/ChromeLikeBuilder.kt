package com.project.starter.easylauncher.plugin.config

import com.project.starter.easylauncher.filter.chromelike.ChromeLikeFilterV2
import com.project.starter.easylauncher.plugin.toColor
import java.awt.Color
import java.io.File

class ChromeLikeBuilder(
    var label: String,
) {
    var ribbonColor: Color? = null
    var labelColor: Color? = null
    var labelPaddingRatio: Float? = null
    var overlayRatio: Float? = null
    var gravity: ChromeLikeFilterV2.Gravity? = null
    var textSizeRatio: Float? = null
    var fontResource: File? = null
    var fontName: String? = null

    fun setRibbonColor(ribbonColor: String) {
        this.ribbonColor = ribbonColor.toColor()
    }

    fun setLabelColor(labelColor: String) {
        this.labelColor = labelColor.toColor()
    }

    fun setGravity(gravity: String) {
        this.gravity = ChromeLikeFilterV2.Gravity.values().firstOrNull {
            it.name.equals(gravity, true)
        } ?: error("Unknown gravity '$gravity'.")
    }

    internal fun build() = ChromeLikeFilterV2(
        label = label,
        ribbonColor = ribbonColor,
        labelColor = labelColor,
        labelPaddingRatio = labelPaddingRatio,
        overlayRatio = overlayRatio,
        gravity = gravity,
        textSizeRatio = textSizeRatio,
        fontResource = fontResource,
        fontName = fontName,
    )
}
