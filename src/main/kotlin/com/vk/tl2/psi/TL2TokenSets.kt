package com.vk.tl2.psi

import com.intellij.psi.tree.TokenSet

object TL2TokenSets {
    val NUMBERS_LITERALS = TokenSet.create(TL2Types.INT_NUMBER)

    val COMMENTS = TokenSet.create(TL2Types.LINE_COMMENT)
}
