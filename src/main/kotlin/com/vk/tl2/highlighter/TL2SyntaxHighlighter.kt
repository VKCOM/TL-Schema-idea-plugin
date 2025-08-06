package com.vk.tl2.highlighter

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.vk.tl2.TL2Lexer
import com.vk.tl2.psi.TL2TokenSets
import com.vk.tl2.psi.TL2Types

class TL2SyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = TL2Lexer()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {

        println(tokenType.toString() + " " + tokenType?.debugName)
        return pack(map(tokenType)?.textAttributesKey)
    }

    companion object {
        fun map(tokenType: IElementType?): TL2HighlightingColors? = when (tokenType) {
            TL2Types.CRC32 -> TL2HighlightingColors.CRC32
            TL2Types.ANNOTATION -> TL2HighlightingColors.ANNOTATION
            TL2Types.USC -> TL2HighlightingColors.USC


            // old
            TL2Types.LC_NAME -> TL2HighlightingColors.LC_NAME
            TL2Types.UC_NAME -> TL2HighlightingColors.UC_NAME



            TokenType.BAD_CHARACTER -> TL2HighlightingColors.BAD_CHARACTER

            in TL2TokenSets.COMMENTS -> TL2HighlightingColors.LINE_COMMENT
//
//            in TL2TokenSets.NUMBERS_LITERALS -> TL2HighlightingColors.NUMBER

            else -> null
        }
    }
}
