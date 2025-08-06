package com.vk.tl2.highlighter

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class TL2HighlightingColors(humanName: String, default: TextAttributesKey) {
    NUMBER("Comments//Line comment", Default.NUMBER),

    LINE_COMMENT("Comments//Line comment", Default.LINE_COMMENT),

    BAD_CHARACTER("Bad character", HighlighterColors.BAD_CHARACTER),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey(
        "com.vk.tl2.highlighter.$name",
        default,
    )
}
