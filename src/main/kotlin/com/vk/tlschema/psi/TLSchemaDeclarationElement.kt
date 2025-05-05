package com.vk.tlschema.psi

interface TLSchemaDeclarationElement : TLSchemaElement {
    fun getCombinator(): TLSchemaLcIdentNs?

    fun getResultType(): TLSchemaResultType

    fun getNumVars(): List<TLSchemaVarIdent>?

    fun getTypeVars(): List<TLSchemaVarIdent>?

    fun haveConditionalArgs(): Boolean

    fun getNamespace(): String
}
