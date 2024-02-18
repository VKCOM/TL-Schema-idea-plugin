package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaLcIdentFull
import com.vk.tlschema.psi.TLSchemaLcIdentNs
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaLcIdentFullMixin(node: ASTNode) : ASTWrapperPsiElement(node), TLSchemaLcIdentFull {
    override fun getDeclaration(): TLSchemaDeclaration? {
        return TLSchemaPsiImplUtil.getDeclaration(this)
    }

    override fun getLcIdentNs(): TLSchemaLcIdentNs {
        return findNotNullChildByClass(TLSchemaLcIdentNs::class.java)
    }
}
