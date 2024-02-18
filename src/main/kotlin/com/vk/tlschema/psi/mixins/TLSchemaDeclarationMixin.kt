package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaCombinatorDecl
import com.vk.tlschema.psi.TLSchemaCombinatorDeclBuiltin
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaLcIdentNs
import com.vk.tlschema.psi.TLSchemaResultType
import com.vk.tlschema.psi.TLSchemaVarIdent
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

internal abstract class TLSchemaDeclarationMixin(node: ASTNode) : ASTWrapperPsiElement(node), TLSchemaDeclaration {
    override fun getCombinatorDecl(): TLSchemaCombinatorDecl? {
        return findChildByClass(TLSchemaCombinatorDecl::class.java)
    }

    override fun getCombinatorDeclBuiltin(): TLSchemaCombinatorDeclBuiltin? {
        return findChildByClass(TLSchemaCombinatorDeclBuiltin::class.java)
    }

    override fun getCombinator(): TLSchemaLcIdentNs? {
        return TLSchemaPsiImplUtil.getCombinator(this)
    }

    override fun getResultType(): TLSchemaResultType {
        return TLSchemaPsiImplUtil.getResultType(this)
    }

    override fun getNumVars(): List<TLSchemaVarIdent>? {
        return TLSchemaPsiImplUtil.getNumVars(this)
    }

    override fun getTypeVars(): List<TLSchemaVarIdent>? {
        return TLSchemaPsiImplUtil.getTypeVars(this)
    }

    override fun haveConditionalArgs(): Boolean {
        return TLSchemaPsiImplUtil.haveConditionalArgs(this)
    }

    override fun getNamespace(): String {
        return TLSchemaPsiImplUtil.getNamespace(this)
    }
}
