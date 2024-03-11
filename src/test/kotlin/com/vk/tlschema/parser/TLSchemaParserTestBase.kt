package com.vk.tlschema.parser

import com.intellij.testFramework.ParsingTestCase
import com.vk.tlschema.TLSchemaParserDefinition
import com.vk.tlschema.TLSchemaTestingUtil

abstract class TLSchemaParserTestBase(folderPath: String) : ParsingTestCase(
    "parser/$folderPath",
    TLSchemaTestingUtil.FILE_EXTENSION,
    true,
    TLSchemaParserDefinition(),
) {
    override fun getTestDataPath() = TLSchemaTestingUtil.getTestDataPath()

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }

    protected fun doTest() {
        super.doTest(true, true)
    }
}
