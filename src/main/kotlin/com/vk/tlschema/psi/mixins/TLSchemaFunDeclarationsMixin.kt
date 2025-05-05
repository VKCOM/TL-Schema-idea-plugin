package com.vk.tlschema.psi.mixins

import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaFunDeclarations
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaFunDeclarationsMixin(node: ASTNode) :
    TLSchemaElementMixin(node), TLSchemaFunDeclarations {
    override fun getNamespaces(result: Set<String>) {
        TLSchemaPsiImplUtil.getNamespaces(this, result)
    }
}
