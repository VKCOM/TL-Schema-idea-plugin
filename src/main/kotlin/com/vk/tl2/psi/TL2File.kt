package com.vk.tl2.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.vk.tl2.TL2FileType
import com.vk.tl2.TL2Language

class TL2File(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TL2Language) {
    override fun getFileType() = TL2FileType

    override fun toString() = "TL2 File"
}
