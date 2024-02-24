package com.vk.tlschema.highlighter

import com.intellij.testFramework.EditorTestUtil
import com.vk.tlschema.TLSchemaTestBase

abstract class TLSchemaSyntaxHighlighterTestBase : TLSchemaTestBase() {
    override val dataPath = "highlighter"

    protected fun doTest() {
        val testName = getTestName(false)
        myFixture.configureByFiles("$testName.tl")

        val answerFilePath = "$testDataPath/$testName.tl.txt"

        EditorTestUtil.testFileSyntaxHighlighting(myFixture.file, answerFilePath, true)
    }
}
