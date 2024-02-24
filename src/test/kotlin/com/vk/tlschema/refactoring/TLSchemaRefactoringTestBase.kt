package com.vk.tlschema.refactoring

import com.vk.tlschema.TLSchemaTestBase

abstract class TLSchemaRefactoringTestBase(folderPath: String) : TLSchemaTestBase() {
    override val dataPath = "refactoring/$folderPath"

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }
}
