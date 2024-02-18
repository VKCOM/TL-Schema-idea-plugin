package com.vk.tlschema.psi

interface TLSchemaNamespacesElement : TLSchemaElement {
    fun getNamespaces(result: Set<String>)
}
