package com.vk.tlschema.lexer

import com.intellij.testFramework.LexerTestCase
import com.vk.tlschema.TLSchemaLexerAdapter
import com.vk.tlschema.TLSchemaTestingUtil

abstract class TLSchemaLexerTestBase : LexerTestCase() {
    override fun createLexer() = TLSchemaLexerAdapter()

    override fun getDirPath() = "${TLSchemaTestingUtil.getTestDataPath()}/lexer"

    override fun getPathToTestDataFile(extension: String): String {
        return dirPath + "/" + getTestName(false) + extension
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }

    protected fun doTest() = super.doFileTest("tl")
}
