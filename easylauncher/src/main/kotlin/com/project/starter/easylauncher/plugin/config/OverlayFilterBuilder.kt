package com.project.starter.easylauncher.plugin.config

import com.project.starter.easylauncher.filter.overlay.OverlayFilterV2
import java.io.File

class OverlayFilterBuilder {
    var file: File? = null

    fun setFile(file: String) {
        this.file = File(file)
    }

    internal fun build() = OverlayFilterV2(
        overlayFile = file ?: error("Missing 'file' property."),
    )
}
