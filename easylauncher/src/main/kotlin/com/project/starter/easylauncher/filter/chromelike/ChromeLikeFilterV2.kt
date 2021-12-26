package com.project.starter.easylauncher.filter.chromelike

import com.project.starter.easylauncher.filter.Canvas
import com.project.starter.easylauncher.filter.EasyLauncherFilter
import com.project.starter.easylauncher.filter.LabelSize
import com.project.starter.easylauncher.filter.getFont
import java.awt.Color
import java.io.File

private const val DEFAULT_MAX_LABEL_WIDTH_RATIO = 0.5185185185f
private const val DEFAULT_MAX_LABEL_HEIGHT_RATIO = 0.5185185185f

class ChromeLikeFilterV2(
    private val label: String,
    private val ribbonColor: Color?,
    private val labelColor: Color?,
    private val labelPaddingRatio: Float?,
    private val overlayRatio: Float?,
    private val gravity: Gravity?,
    textSizeRatio: Float?,
    fontResource: File?,
    fontName: String?,
) : EasyLauncherFilter {

    enum class Gravity {
        Top, Bottom
    }

    @Transient
    private val font = getFont(
        resource = fontResource,
        name = fontName,
    )

    @Transient
    private val labelSize = textSizeRatio?.let { LabelSize.Fixed(it) }
        ?: LabelSize.Auto(DEFAULT_MAX_LABEL_WIDTH_RATIO, DEFAULT_MAX_LABEL_HEIGHT_RATIO)

    override fun apply(canvas: Canvas, adaptive: Boolean) {
        ChromeLikeFilterRenderer(
            gravity = gravity ?: Gravity.Bottom,
            ribbonColor = ribbonColor ?: Color.DARK_GRAY,
            labelColor = labelColor ?: Color.WHITE,
            font = font,
            overlayRatio = overlayRatio ?: OVERLAY_RATIO,
            labelSize = labelSize,
            labelPaddingRatio = labelPaddingRatio ?: 0f,
            ignoreAlpha = !adaptive,
        ).render(
            canvas = canvas,
            label = label,
        )
    }

    companion object {
        private const val OVERLAY_RATIO = 0.4f
    }
}
