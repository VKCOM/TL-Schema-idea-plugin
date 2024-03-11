package com.vk.tlschema.resolve

import com.vk.tlschema.psi.TLSchemaLcIdentNs
import com.vk.tlschema.psi.TLSchemaUcIdentNs
import com.vk.tlschema.psi.TLSchemaVarIdent

class TLSchemaResolveTest : TLSchemaResolveTestBase() {
    // type parameter
    fun `test type parameter and nat parameter`() = doTest(TLSchemaVarIdent::class.java)
    fun `test type parameter and field masks 1`() = doTest(TLSchemaVarIdent::class.java)
    fun `test type parameter and field masks 2`() = doTest(TLSchemaVarIdent::class.java)

    // type combinator
    fun `test type combinator and the name combinator`() = doTest(TLSchemaLcIdentNs::class.java)

    fun `test nat parameter and constructor combinator`() = doTest(TLSchemaUcIdentNs::class.java)
}
