package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaLcIdentNode
import com.vk.tlschema.psi.TLSchemaUcIdentNode
import com.vk.tlschema.psi.TLSchemaVarIdent
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaVarIdentMixin(node: ASTNode) : ASTWrapperPsiElement(node), TLSchemaVarIdent {
    override fun setName(newName: String): PsiElement {
        return TLSchemaPsiImplUtil.setName(this, newName)
    }

    override fun getName(): String? {
        return TLSchemaPsiImplUtil.getName(this)
    }

    override fun getLcIdentNode(): TLSchemaLcIdentNode? {
        return findChildByClass(TLSchemaLcIdentNode::class.java)
    }

    override fun getUcIdentNode(): TLSchemaUcIdentNode? {
        return findChildByClass(TLSchemaUcIdentNode::class.java)
    }

    override fun getDeclaration(): TLSchemaDeclaration {
        return TLSchemaPsiImplUtil.getDeclaration(this)
    }

    override fun getReference(): PsiReference? {
        return TLSchemaPsiImplUtil.getReference(this)
    }

    override fun getReferences(): Array<PsiReference> {
        return TLSchemaPsiImplUtil.getReferences(this)
    }

    override fun getPresentation(): ItemPresentation? {
        return TLSchemaPsiImplUtil.getPresentation(this)
    }
}