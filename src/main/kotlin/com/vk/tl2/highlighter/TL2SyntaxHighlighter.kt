package com.vk.tl2.highlighter

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.vk.tl2.TL2Lexer
import com.vk.tl2.psi.TL2TokenSets

class TL2SyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = TL2Lexer()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        pack(map(tokenType)?.textAttributesKey)

    companion object {
        fun map(tokenType: IElementType?): TL2HighlightingColors? = when (tokenType) {
            TokenType.BAD_CHARACTER -> TL2HighlightingColors.BAD_CHARACTER

            in TL2TokenSets.COMMENTS -> TL2HighlightingColors.LINE_COMMENT

            in TL2TokenSets.NUMBERS_LITERALS -> TL2HighlightingColors.NUMBER

            else -> null
        }
    }
}
