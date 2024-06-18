package com.vk.tlschema.lang

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.vk.tlschema.TLSchemaFileType

class TLSchemaFileTypeTest : BasePlatformTestCase() {
    private val fileType = TLSchemaFileType.INSTANCE

    fun `test combined_tl file`() = toTest("combined.tl")

    private fun toTest(fileName: String) {
        val file = myFixture.configureByText(fileName, "")

        assertEquals(fileType, file.fileType)
    }
}
