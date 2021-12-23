package com.project.starter.easylauncher.filter.ribbon

import com.project.starter.easylauncher.filter.Canvas
import com.project.starter.easylauncher.filter.LabelSize
import com.project.starter.easylauncher.filter.ribbon.ColorRibbonFilterV2.Gravity
import com.project.starter.easylauncher.filter.scale
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import java.awt.geom.Rectangle2D
import kotlin.math.roundToInt
import kotlin.math.sqrt

class ColorRibbonFilterRenderer(
    private val ribbonColor: Color,
    private val labelColor: Color,
    private val gravity: Gravity,
    private val font: Font,
    private val labelSize: LabelSize,
    private val paddingRatio: Float,
    private val ignoreAlpha: Boolean,
) {
    fun render(canvas: Canvas, label: String) = canvas.use { graphics ->
        // rotate canvas if needed
        graphics.translate(-canvas.paddingLeft, -canvas.paddingTop)
        graphics.transformRotation(gravity.toRotationAngle(), canvas.centerX, canvas.centerY)
        graphics.translate(canvas.paddingLeft, canvas.paddingTop)

        // calculate label size
        val frc = FontRenderContext(graphics.transform, true, true)
        graphics.font = font.scale(
            size = labelSize,
            label = label,
            imageHeight = canvas.height,
            containerWidth = canvas.width,
            containerHeight = canvas.height,
            frc = frc,
        )
        val textBounds = graphics.font.getStringBounds(label, frc)
        val labelHeight = getLabelHeight(textBounds)

        // calculate the drawing rectangle
        val padding = (canvas.height * paddingRatio).roundToInt()
        val background = when (gravity) {
            Gravity.Top ->
                Rectangle(
                    -canvas.paddingLeft,
                    padding,
                    canvas.fullWidth,
                    labelHeight,
                )

            Gravity.Bottom ->
                Rectangle(
                    -canvas.paddingLeft,
                    canvas.height - labelHeight - padding,
                    canvas.fullWidth,
                    labelHeight,
                )

            Gravity.TopLeft, Gravity.TopRight -> {
                // If rotated, we extend the width to the size of the diagonal to ensure that the ribbon is not too short
                val diagonalWidth = (canvas.fullWidth * sqrt(2f)).roundToInt()
                val diagonalPadding = ((diagonalWidth - canvas.fullWidth) / 2f).roundToInt()
                Rectangle(
                    -canvas.paddingLeft - diagonalPadding,
                    padding,
                    diagonalWidth,
                    labelHeight,
                )
            }
        }

        // draw the ribbon
        if (ignoreAlpha) {
            graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f)
        }
        graphics.color = ribbonColor
        graphics.fillRect(background.x, background.y, background.width, background.height)

        // draw the label
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.color = labelColor
        graphics.drawString(
            label,
            (background.centerX - textBounds.centerX).toFloat(),
            (background.centerY - textBounds.centerY).toFloat(),
        )
    }

    private fun getLabelHeight(textBounds: Rectangle2D): Int {
        val textHeight = textBounds.height.toFloat()
        val textPadding = textHeight / 10f
        return (textHeight + 2f * textPadding).roundToInt()
    }

    private fun Gravity.toRotationAngle() = when (this) {
        Gravity.Top, Gravity.Bottom -> 0.0
        Gravity.TopRight -> 45.0
        Gravity.TopLeft -> -45.0
    }

    private fun Graphics2D.transformRotation(rotation: Double, centerX: Double, centerY: Double) {
        if (rotation != 0.0) {
            transform =
                AffineTransform.getRotateInstance(Math.toRadians(rotation), centerX, centerY)
        }
    }

    private val Canvas.centerX
        get() = fullWidth / 2.0

    private val Canvas.centerY
        get() = fullHeight / 2.0
}
