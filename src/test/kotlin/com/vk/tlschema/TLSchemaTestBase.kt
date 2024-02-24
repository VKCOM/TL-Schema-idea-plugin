package com.vk.tlschema

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Ignore

@Ignore
abstract class TLSchemaTestBase : BasePlatformTestCase() {
    open val dataPath: String = ""

    override fun getTestDataPath(): String {
        return TLSchemaTestingUtil.getTestDataPath() + "/" + dataPath
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        val name = super.getTestName(lowercaseFirstLetter)
        return name.trimStart()
    }
}
