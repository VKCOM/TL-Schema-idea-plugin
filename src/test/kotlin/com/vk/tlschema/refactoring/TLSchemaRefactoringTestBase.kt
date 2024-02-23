package com.vk.tlschema.refactoring

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.vk.tlschema.TLSchemaTestingUtil

abstract class TLSchemaRefactoringTestBase(private val folderPath: String) : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return TLSchemaTestingUtil.getTestDataPath() + "/refactoring/" + folderPath
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }
}
