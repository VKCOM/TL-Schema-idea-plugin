package com.vk.tlschema.lang

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.intellij.lang.annotations.Language

class TLSchemaCommenterTest : BasePlatformTestCase() {
    fun `test single line comment`() = doTest(
        """
        int#a8509bda ? = Int<caret>;
        boolFalse#<caret>bc799737 = Bool;
        <caret>boolStat statTrue:int statFalse:int statUnknown:int = BoolStat;
        statOne
          key:string
          <caret>value:string
          = StatOne;
        """,
        """
        //int#a8509bda ? = Int;
        //boolFalse#bc799737 = Bool;
        //boolStat statTrue:int statFalse:int statUnknown:int = BoolStat;
        statOne
          key:string
        //  value:string
          = StatOne;
        """,
    )

    fun `test single line uncomment`() = doTest(
        """
        //int#a8509bda ? = Int<caret>;
        //boolFalse#<caret>bc799737 = Bool;
        //<caret>boolStat statTrue:int statFalse:int statUnknown:int = BoolStat;
        statOne
          key:string
        //  <caret>value:string
          = StatOne;
        """,
        """
        int#a8509bda ? = Int;
        boolFalse#bc799737 = Bool;
        boolStat statTrue:int statFalse:int statUnknown:int = BoolStat;
        statOne
          key:string
          value:string
          = StatOne;
        """,
    )

    private fun doTest(@Language("TLSchema") beforeText: String, @Language("TLSchema") afterText: String) {
        myFixture.configureByText("todo.tl", beforeText.trimIndent())
        myFixture.performEditorAction(IdeActions.ACTION_COMMENT_LINE)
        myFixture.checkResult(afterText.trimIndent())
    }
}
