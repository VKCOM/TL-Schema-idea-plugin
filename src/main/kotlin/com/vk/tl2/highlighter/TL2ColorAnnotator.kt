package com.vk.tl2.highlighter

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.vk.tl2.psi.TL2FuncDeclaration
import com.vk.tl2.psi.TL2TypeDeclaration
import com.vk.tl2.psi.TL2TypeName

class TL2ColorAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is TL2TypeName) {
            if (element.parent is TL2FuncDeclaration) {
                setHighlighting(holder, element, TL2HighlightingColors.FUNC_DECLARATION.textAttributesKey)
                return
            }

            if (element.parent is TL2TypeDeclaration) {
                setHighlighting(holder, element, TL2HighlightingColors.TYPE_DECLARATION.textAttributesKey)
                return
            }


//            println("!!! " + element.text)
        }
    }


    private fun setHighlighting(holder: AnnotationHolder, element: PsiElement, key: TextAttributesKey) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element)
            .textAttributes(key).create();
    }


}
