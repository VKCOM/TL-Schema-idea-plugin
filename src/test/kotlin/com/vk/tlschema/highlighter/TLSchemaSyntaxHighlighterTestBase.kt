package com.vk.tlschema.highlighter

import com.intellij.testFramework.EditorTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.vk.tlschema.TLSchemaTestingUtil

abstract class TLSchemaSyntaxHighlighterTestBase : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return TLSchemaTestingUtil.getTestDataPath() + "/highlighter"
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }

    protected fun doTest() {
        val testName = getTestName(false)
        myFixture.configureByFiles("$testName.tl")

        val answerFilePath = "$testDataPath/$testName.tl.txt"

        EditorTestUtil.testFileSyntaxHighlighting(myFixture.file, answerFilePath, true)
    }
}
