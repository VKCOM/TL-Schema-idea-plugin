package com.vk.tlschema

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import org.junit.Ignore

@Ignore
abstract class TLSchemaTestBase : BasePlatformTestCase() {
    abstract val dataPath: String

    override fun getTestDataPath(): String {
        return TLSchemaTestingUtil.getTestDataPath() + "/" + dataPath
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }

    fun CodeInsightTestFixture.configureByTLFile(filePath: String) {
        configureByFile(filePath + "." + TLSchemaTestingUtil.FILE_EXTENSION)
    }

    fun CodeInsightTestFixture.checkResultByTLFile(filePath: String) {
        checkResultByFile(filePath + "." + TLSchemaTestingUtil.FILE_EXTENSION)
    }
}
