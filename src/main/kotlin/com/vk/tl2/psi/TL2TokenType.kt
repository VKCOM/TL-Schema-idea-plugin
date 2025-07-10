package com.vk.tl2.psi

import com.intellij.psi.tree.IElementType
import com.vk.tl2.TL2Language
import org.jetbrains.annotations.NonNls

class TL2TokenType(@NonNls debugName: String) : IElementType(debugName, TL2Language) {
    override fun toString() = "TL2TokenType." + super.toString()
}
