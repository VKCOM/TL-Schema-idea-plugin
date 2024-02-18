package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import com.vk.tlschema.psi.TLSchemaBoxedTypeIdent
import com.vk.tlschema.psi.TLSchemaDeclaration
import com.vk.tlschema.psi.TLSchemaResultType
import com.vk.tlschema.psi.TLSchemaTypeExpr
import com.vk.tlschema.psi.impl.TLSchemaPsiImplUtil

abstract class TLSchemaResultTypeMixin(node: ASTNode) : ASTWrapperPsiElement(node), TLSchemaResultType {
    override fun getDeclaration(): TLSchemaDeclaration? {
        return TLSchemaPsiImplUtil.getDeclaration(this)
    }

    override fun getBoxedTypeIdent(): TLSchemaBoxedTypeIdent {
        return findNotNullChildByClass(TLSchemaBoxedTypeIdent::class.java)
    }

    override fun getTypeExprList(): List<TLSchemaTypeExpr> {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TLSchemaTypeExpr::class.java)
    }
}
