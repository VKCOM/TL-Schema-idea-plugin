package com.vk.tlschema.psi.mixins

import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaResultType
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaResultTypeMixin(node: ASTNode) : TLSchemaElementMixin(node), TLSchemaResultType {
    override fun getDeclaration(): TLSchemaDeclaration? {
        return TLSchemaPsiImplUtil.getDeclaration(this)
    }
}
