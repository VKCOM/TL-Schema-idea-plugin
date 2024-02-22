package com.vk.tlschema.parser

class TLSchemaFunctionsParserTest : TLSchemaParserTestBase("functions") {
    // Шаблонные параметры
    fun `test template parameters`() = doTest()

    // Маски полей ответа
    fun `test response field masks`() = doTest()

    // Пространства имён
    fun `test namespaces`() = doTest()

    // Аннотации
    fun `test annotations`() = doTest()
}
