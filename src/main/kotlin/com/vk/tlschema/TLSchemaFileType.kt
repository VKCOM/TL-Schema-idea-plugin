package com.vk.tlschema

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object TLSchemaFileType : LanguageFileType(TLSchemaLanguage.INSTANCE) {
    override fun getName(): String = "TLSchema"

    override fun getDescription(): String = "TL schema file"

    override fun getDefaultExtension(): String = "tl"

    override fun getIcon(): Icon = TLSchemaIcons.FILE
}
