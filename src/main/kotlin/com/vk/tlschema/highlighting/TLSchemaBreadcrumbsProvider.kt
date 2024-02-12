package com.vk.tlschema.highlighting

import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider
import com.vk.tlschema.TLSchemaLanguage
import com.vk.tlschema.psi.TLSchemaConstructorDeclarations
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaFunDeclarations

class TLSchemaBreadcrumbsProvider : BreadcrumbsProvider {
    override fun getLanguages() = arrayOf(TLSchemaLanguage.INSTANCE)

    override fun acceptElement(element: PsiElement) : Boolean {
        return when (element) {
            is TLSchemaFunDeclarations,
            is TLSchemaConstructorDeclarations,
            is TLSchemaDeclaration -> true

            else -> false
        }
    }

    @NlsSafe
    override fun getElementInfo(psiElement: PsiElement) : String {
        return when (psiElement) {
            is TLSchemaFunDeclarations -> "functions"
            is TLSchemaConstructorDeclarations -> "types"
            is TLSchemaDeclaration -> psiElement.getCombinator().getName()!!
            else -> throw IllegalArgumentException("Not supported")
        }
    }
}
