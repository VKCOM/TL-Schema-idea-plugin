package com.vk.tlschema.refactoring

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.vk.tlschema.psi.TLSchemaNamesValidator

class TLSchemaNamesValidatorTest : BasePlatformTestCase() {
    private val validator = TLSchemaNamesValidator()

    private fun isIdentifier(string: String) = validator.isIdentifier(string, null)

    fun `test identifier`() {
        assertTrue(isIdentifier("field"))
        assertTrue(isIdentifier("Field"))
        assertTrue(isIdentifier("fieldName"))
        assertTrue(isIdentifier("field_name"))
        assertTrue(isIdentifier("field_name1"))
        assertTrue(isIdentifier("group_name.field_name"))
        assertTrue(isIdentifier("group_name.field_name"))
        assertTrue(isIdentifier("groupName.fieldName"))

        assertFalse(isIdentifier(""))
        assertFalse(isIdentifier(" "))
        assertFalse(isIdentifier("_field_name"))
        assertFalse(isIdentifier("field_name "))
        assertFalse(isIdentifier("1field_name"))
        assertFalse(isIdentifier("group_name."))
        assertFalse(isIdentifier("GroupName.field_name"))
        assertFalse(isIdentifier("1group_name.field_name"))
        assertFalse(isIdentifier("group_name.1field_name"))
        assertFalse(isIdentifier("имя_поля"))
    }
}
