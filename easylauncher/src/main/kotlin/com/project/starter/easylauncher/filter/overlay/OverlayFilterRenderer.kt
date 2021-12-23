package com.project.starter.easylauncher.filter.overlay

import com.project.starter.easylauncher.filter.Canvas
import java.awt.Image
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

class OverlayFilterRenderer(
    private val scale: Float,
) {
    fun render(canvas: Canvas, overlayImage: BufferedImage) = canvas.use { graphics ->
        val fgWidth = overlayImage.getWidth(null).toFloat()
        val fgHeight = overlayImage.getHeight(null).toFloat()

        val imageScale = scale * (canvas.width / fgWidth).coerceAtMost(canvas.height / fgHeight)
        val scaledWidth = (fgWidth * imageScale).roundToInt()
        val scaledHeight = (fgHeight * imageScale).roundToInt()
        val fgImageScaled = overlayImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH)

        // TODO allow to choose the gravity for the overlay
        // TODO allow to choose the scaling type
        graphics.drawImage(
            fgImageScaled,
            ((canvas.width - scaledWidth) / 2f).roundToInt(),
            ((canvas.height - scaledHeight) / 2f).roundToInt(),
            null,
        )
    }
}
