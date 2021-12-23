package com.project.starter.easylauncher.filter.overlay

import com.project.starter.easylauncher.filter.Canvas
import com.project.starter.easylauncher.filter.EasyLauncherFilter
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

/** Default scaling of overlay image. */
private const val DEFAULT_SCALE = 1f

/**
 * Overlay another image over the icon.
 *
 * @param overlayFile The overlay image to render.
 */
@Suppress("MagicNumber")
class OverlayFilterV2(
    private val overlayFile: File,
) : EasyLauncherFilter {

    private val logger
        get() = LoggerFactory.getLogger(this::class.java)

    override fun apply(canvas: Canvas, adaptive: Boolean) {
        try {
            ImageIO.read(overlayFile)?.let { overlayImage ->
                OverlayFilterRenderer(
                    scale = DEFAULT_SCALE,
                ).render(
                    canvas = canvas,
                    overlayImage = overlayImage,
                )
            }
        } catch (e: IOException) {
            logger.error("Failed to load overlay ${overlayFile.name}", e)
        }
    }
}
