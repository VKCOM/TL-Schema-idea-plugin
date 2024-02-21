package com.vk.tlschema.parser

class TLSchemaTypesParserTest : TLSchemaParserTestBase("types") {
    // Целые типы и простые комбинаторы
    fun `test simple combinators`() = doTest()

    // Теги, голые и коробочные представления типа
    fun `test tags`() = doTest()

    // Объединения
    fun `test unions`() = doTest()

    // Маски полей
    fun `test field masks`() = doTest()

    // Nat-параметры
    fun `test nat parameters`() = doTest()

    // Встроенные массивы
    fun `test arrays`() = doTest()

    // Type-параметры
    fun `test type parameters`() = doTest()

    // Анонимные типы
    fun `test anonymous types`() = doTest()

    // Анонимные поля и множители
    fun `test anonymous fields`() = doTest()
}
