package com.vk.tlschema.psi.mixins

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.vk.tlschema.psi.TLSchemaElement

internal abstract class TLSchemaElementMixin(node: ASTNode) : ASTWrapperPsiElement(node), TLSchemaElement
