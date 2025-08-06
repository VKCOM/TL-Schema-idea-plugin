package com.vk.tl2

import com.intellij.openapi.fileTypes.LanguageFileType
import com.vk.kphp.wingman.icon.TL2Icons
import javax.swing.Icon

object TL2FileType : LanguageFileType(TL2Language) {
    override fun getName(): String = "TL2"

    override fun getDescription(): String = "TL2 file"

    override fun getDefaultExtension(): String = "tl2"

    override fun getIcon(): Icon = TL2Icons.General.TL2File
}
