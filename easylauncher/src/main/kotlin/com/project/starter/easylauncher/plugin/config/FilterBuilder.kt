package com.project.starter.easylauncher.plugin.config

import com.project.starter.easylauncher.filter.EasyLauncherFilter

class FilterBuilder(
    private val name: String,
) {
    private val filters = mutableListOf<EasyLauncherFilter>()

    fun ribbon(builder: RibbonBuilder.() -> Unit) {
        filters.add(RibbonBuilder(name).apply(builder).build())
    }

    fun chromeLike(builder: ChromeLikeBuilder.() -> Unit) {
        filters.add(ChromeLikeBuilder(name).apply(builder).build())
    }

    fun overlay(builder: OverlayFilterBuilder.() -> Unit) {
        filters.add(OverlayFilterBuilder().apply(builder).build())
    }

    internal fun build(): List<EasyLauncherFilter> = filters
}
