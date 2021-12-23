package com.project.starter.easylauncher.filter

import java.awt.Font
import java.awt.font.FontRenderContext
import java.io.Serializable

sealed class LabelSize : Serializable {
    class Fixed(
        val ratio: Float,
    ) : LabelSize()

    class Auto(
        val maxWidthRatio: Float,
        val maxHeightRatio: Float,
    ) : LabelSize()

    companion object {
        const val serialVersionUID = 1L
    }
}

fun Font.scale(
    size: LabelSize,
    label: String,
    imageHeight: Int,
    containerWidth: Int,
    containerHeight: Int,
    frc: FontRenderContext,
): Font =
    when (size) {
        is LabelSize.Fixed -> deriveFont((imageHeight * size.ratio))
        is LabelSize.Auto -> scaleFit(
            label = label,
            maxFontSize = containerHeight,
            maxWidth = containerWidth * size.maxWidthRatio,
            maxHeight = containerHeight * size.maxHeightRatio,
            frc = frc,
        )
    }

/**
 * Scale this [Font] until the [label] fits into [maxWidth] and [maxHeight].
 */
private fun Font.scaleFit(
    label: String,
    maxFontSize: Int,
    maxWidth: Float,
    maxHeight: Float,
    frc: FontRenderContext,
) = (maxFontSize downTo 0).asSequence()
    .map { size -> deriveFont(size.toFloat()) }
    .first { font ->
        val bounds = font.getStringBounds(label, frc)
        bounds.width <= maxWidth && bounds.height <= maxHeight
    }
