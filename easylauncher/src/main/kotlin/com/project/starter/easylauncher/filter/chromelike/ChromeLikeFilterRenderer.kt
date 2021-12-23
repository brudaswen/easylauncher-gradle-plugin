package com.project.starter.easylauncher.filter.chromelike

import com.project.starter.easylauncher.filter.Canvas
import com.project.starter.easylauncher.filter.LabelSize
import com.project.starter.easylauncher.filter.chromelike.ChromeLikeFilterV2.Gravity
import com.project.starter.easylauncher.filter.scale
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.font.FontRenderContext
import kotlin.math.roundToInt

class ChromeLikeFilterRenderer(
    private val gravity: Gravity,
    private val ribbonColor: Color,
    private val labelColor: Color,
    private val font: Font,
    private val overlayRatio: Float,
    private val labelSize: LabelSize,
    private val labelPadding: Int,
    private val ignoreAlpha: Boolean,
) {
    fun render(canvas: Canvas, label: String) = canvas.use { graphics ->
        // calculate the rectangle where the label is rendered
        val overlayHeight = (canvas.height * overlayRatio).roundToInt()
        val background = when (gravity) {
            Gravity.Top -> Rectangle(
                -canvas.paddingLeft,
                -canvas.paddingTop,
                canvas.fullWidth,
                overlayHeight + canvas.paddingTop,
            )
            Gravity.Bottom -> Rectangle(
                -canvas.paddingLeft,
                canvas.height - overlayHeight,
                canvas.fullWidth,
                overlayHeight + canvas.paddingBottom,
            )
        }

        // calculate label size
        val frc = FontRenderContext(graphics.transform, true, true)
        graphics.font = font.scale(
            size = labelSize,
            label = label,
            imageHeight = canvas.height,
            containerWidth = canvas.width,
            containerHeight = overlayHeight,
            frc = frc,
        )
        val textBounds = graphics.font.getStringBounds(label, frc)

        // only draw on top of existing pixels
        if (ignoreAlpha) {
            graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f)
        }

        // draw the ribbon
        graphics.color = ribbonColor
        graphics.fillRect(background.x, background.y, background.width, background.height)

        // draw the label
        val labelX = (background.centerX - textBounds.centerX).toFloat()
        val labelY = when (gravity) {
            Gravity.Top -> background.maxY.toFloat() - graphics.fontMetrics.descent - labelPadding
            Gravity.Bottom -> background.y.toFloat() + graphics.fontMetrics.ascent + labelPadding
        }
        graphics.setPaintMode()
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.color = labelColor
        graphics.drawString(label, labelX, labelY)
    }
}
