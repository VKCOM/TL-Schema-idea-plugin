package com.vk.tlschema.refactoring

class TLSchemaRenameRefactoringTest : TLSchemaRefactoringTestBase("rename") {
    fun `test lc_ident_ns reference`() = doTest("int32")
    fun `test uc_ident_ns reference`() = doTest("MyVector")
    fun `test var_ident reference`() = doTest("funny_mask")

    private fun doTest(newName: String) {
        val testName = getTestName(false)

        myFixture.configureByTLFile(testName)
        myFixture.renameElementAtCaret(newName)
        myFixture.checkResultByTLFile("$testName.after")
    }
}
