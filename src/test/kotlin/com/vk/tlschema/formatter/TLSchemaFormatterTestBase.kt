package com.vk.tlschema.formatter

import com.intellij.psi.formatter.FormatterTestCase

abstract class TLSchemaFormatterTestBase : FormatterTestCase() {
    override fun getBasePath() = "formatter"

    // TODO
    override fun getTestDataPath() = "src/test/resources/com/vk/tlschema"

    override fun getFileExtension() = "tl"

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }

    override fun doTest() {
        val testName = getTestName(false)

        doTest(testName, "$testName.after")
    }
}
