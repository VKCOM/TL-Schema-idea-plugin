package com.vk.tlschema.highlighting

import com.intellij.lang.Language
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider
import com.vk.tlschema.TLSchemaLanguage
import com.vk.tlschema.psi.TLSchemaConstructorDeclarations
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaFunDeclarations
import java.util.*

class TLSchemaBreadcrumbsProvider : BreadcrumbsProvider {
    override fun getLanguages(): Array<Language> {
        return arrayOf(TLSchemaLanguage.INSTANCE)
    }

    override fun acceptElement(psiElement: PsiElement): Boolean {
        return (psiElement is TLSchemaFunDeclarations
                || psiElement is TLSchemaConstructorDeclarations
                || psiElement is TLSchemaDeclaration)
    }

    override fun getElementInfo(psiElement: PsiElement): @NlsSafe String {
        return when (psiElement) {
            is TLSchemaFunDeclarations -> {
                "functions"
            }

            is TLSchemaConstructorDeclarations -> {
                "types"
            }

            is TLSchemaDeclaration -> {
                psiElement.getCombinator().getName()!!
            }

            else -> throw IllegalArgumentException("Not supported")
        }
    }
}
