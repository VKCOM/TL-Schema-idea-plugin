package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaFunDeclarations
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaFunDeclarationsMixin(node: ASTNode) :
    ASTWrapperPsiElement(node), TLSchemaFunDeclarations {
    override fun getNamespaces(result: Set<String>) {
        TLSchemaPsiImplUtil.getNamespaces(this, result)
    }

    override fun getDeclarationList(): List<TLSchemaDeclaration> {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TLSchemaDeclaration::class.java)
    }
}
