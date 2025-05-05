package com.vk.tlschema.psi.mixins

import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaConstructorDeclarations
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaConstructorDeclarationsMixin(node: ASTNode) :
    TLSchemaElementMixin(node), TLSchemaConstructorDeclarations {
    override fun getNamespaces(result: Set<String>) {
        TLSchemaPsiImplUtil.getNamespaces(this, result)
    }
}
