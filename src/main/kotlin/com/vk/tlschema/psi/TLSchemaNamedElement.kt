package com.vk.tlschema.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiReference

interface TLSchemaNamedElement : TLSchemaElement, PsiNamedElement, NavigationItem {
    override fun getName(): String?

    override fun setName(newName: String): PsiElement

    override fun getReference(): PsiReference?

    override fun getReferences(): Array<PsiReference>

    override fun getPresentation(): ItemPresentation?
}
