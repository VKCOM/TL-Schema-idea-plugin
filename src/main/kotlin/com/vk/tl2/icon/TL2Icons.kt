package com.vk.kphp.wingman.icon

import com.intellij.ui.IconManager

object TL2Icons {
    private fun icon(name: String) = IconManager.getInstance().getIcon(name, javaClass.classLoader)

    object General {
        val TL2File = icon("/icons/tl2File.svg")
    }
}
