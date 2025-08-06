package com.vk.tl2.highlighter

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import com.intellij.openapi.editor.colors.CodeInsightColors;

enum class TL2HighlightingColors(humanName: String, default: TextAttributesKey) {
    NUMBER("Comments//Line comment", Default.NUMBER),

    LINE_COMMENT("Comments//Line comment", Default.LINE_COMMENT),

    BAD_CHARACTER("Bad character", HighlighterColors.BAD_CHARACTER),

    CRC32("CRC32", Default.METADATA),

    ANNOTATION("Annotation", Default.METADATA),

    USC("Underscore", CodeInsightColors.NOT_USED_ELEMENT_ATTRIBUTES),

    FUNC_DECLARATION("Func Declaration", Default.CLASS_NAME),
    TYPE_DECLARATION("Type Declaration", Default.CLASS_NAME),

    LC_NAME("LC Name", Default.NUMBER),
    UC_NAME("UC Name", Default.FUNCTION_DECLARATION),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey(
        "com.vk.tl2.highlighter.$name",
        default,
    )
}
