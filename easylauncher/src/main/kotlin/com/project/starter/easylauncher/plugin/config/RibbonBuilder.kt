package com.project.starter.easylauncher.plugin.config

import com.project.starter.easylauncher.filter.ribbon.ColorRibbonFilterV2
import com.project.starter.easylauncher.plugin.toColor
import java.awt.Color
import java.io.File

class RibbonBuilder(
    var label: String,
) {
    var ribbonColor: Color? = null
    var labelColor: Color? = null
    var gravity: ColorRibbonFilterV2.Gravity? = null
    var textSizeRatio: Float? = null
    var fontName: String? = null
    var fontResource: File? = null
    var paddingRatio: Float? = null
    var ignoreTransparentPixels: Boolean? = null

    fun setRibbonColor(ribbonColor: String) {
        this.ribbonColor = ribbonColor.toColor()
    }

    fun setLabelColor(labelColor: String) {
        this.labelColor = labelColor.toColor()
    }

    fun setGravity(gravity: String) {
        this.gravity = ColorRibbonFilterV2.Gravity.values().firstOrNull {
            it.name.equals(gravity, true)
        } ?: error("Unknown gravity '$gravity'.")
    }

    internal fun build() = ColorRibbonFilterV2(
        label = label,
        ribbonColor = ribbonColor,
        labelColor = labelColor,
        gravity = gravity,
        textSizeRatio = textSizeRatio,
        fontName = fontName,
        fontResource = fontResource,
        paddingRatio = paddingRatio,
        ignoreTransparentPixels = ignoreTransparentPixels,
    )
}
