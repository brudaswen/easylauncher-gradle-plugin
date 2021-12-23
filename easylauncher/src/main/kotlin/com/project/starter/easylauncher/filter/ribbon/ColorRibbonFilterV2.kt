package com.project.starter.easylauncher.filter.ribbon

import com.project.starter.easylauncher.filter.Canvas
import com.project.starter.easylauncher.filter.EasyLauncherFilter
import com.project.starter.easylauncher.filter.LabelSize
import com.project.starter.easylauncher.filter.getFont
import java.awt.Color
import java.io.File

private const val DEFAULT_MAX_LABEL_WIDTH_RATIO = 0.75f
private const val DEFAULT_MAX_LABEL_HEIGHT_RATIO = 0.2f

private const val DEFAULT_PADDING_RATIO = 0.1f

private val DEFAULT_RIBBON_COLOR = Color(0, 0x72, 0, 0x99)
private val DEFAULT_LABEL_COLOR = Color.WHITE

@Suppress("MagicNumber")
class ColorRibbonFilterV2(
    private val label: String,
    private val ribbonColor: Color?,
    private val labelColor: Color?,
    private val gravity: Gravity?,
    textSizeRatio: Float?,
    fontName: String?,
    fontResource: File?,
    private val paddingRatio: Float?,
    private val ignoreTransparentPixels: Boolean?,
) : EasyLauncherFilter {

    enum class Gravity {
        Top, Bottom, TopLeft, TopRight
    }

    @Transient
    private val font = getFont(
        resource = fontResource,
        name = fontName,
    )

    @Transient
    private val labelSize = textSizeRatio?.let { LabelSize.Fixed(it) }
        ?: LabelSize.Auto(DEFAULT_MAX_LABEL_WIDTH_RATIO, DEFAULT_MAX_LABEL_HEIGHT_RATIO)

    @Suppress("ComplexMethod")
    override fun apply(canvas: Canvas, adaptive: Boolean) {
        val gravity = gravity ?: Gravity.TopLeft
        ColorRibbonFilterRenderer(
            ribbonColor = ribbonColor ?: DEFAULT_RIBBON_COLOR,
            labelColor = labelColor ?: DEFAULT_LABEL_COLOR,
            gravity = gravity,
            font = font,
            labelSize = labelSize,
            paddingRatio = paddingRatio ?: DEFAULT_PADDING_RATIO,
            ignoreAlpha = ignoreTransparentPixels == true && !adaptive,
        ).render(
            canvas = canvas,
            label = label,
        )
    }
}
