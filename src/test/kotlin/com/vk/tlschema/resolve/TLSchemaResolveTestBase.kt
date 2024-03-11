package com.vk.tlschema.resolve

import com.intellij.lang.LanguageCommenters
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.vk.tlschema.TLSchemaTestBase
import com.vk.tlschema.psi.TLSchemaNamedElement

abstract class TLSchemaResolveTestBase : TLSchemaTestBase() {
    override val dataPath = "resolve"

    protected fun <T : PsiElement> doTest(targetPsiClass: Class<T>) {
        val testName = getTestName(false)
        myFixture.configureByTLFile(testName)

        val targetElement = myFixture.findElementBeforeTag(targetPsiClass, "target")
        val referenceElement = myFixture.findElementBeforeTag(TLSchemaNamedElement::class.java, "ref")

        val resolveElement = referenceElement.reference?.resolve()
        check(resolveElement != null) { "Couldn't resolve the reference of the '$referenceElement' element" }

        check(resolveElement == targetElement) {
            "$referenceElement was expected to reference $targetElement, but it references $resolveElement"
        }
    }

    private fun <T : PsiElement> CodeInsightTestFixture.findElementBeforeTag(
        psiClass: Class<T>,
        markerName: String,
    ): T {
        val document = file?.viewProvider?.document ?: error("Can't find document")
        val commentPrefix = LanguageCommenters.INSTANCE.forLanguage(file.language)?.lineCommentPrefix ?: "//"
        val text = document.text

        val point = "#" // Indicates the beginning of the element above it
        val markerPrefix = "$commentPrefix $point"
        val findMarker = "$markerPrefix$markerName"

        val commentMarkerOffset = text.indexOf(findMarker)
        check(commentMarkerOffset != -1) { "Can't find the '$findMarker' comment" }

        val pointOffset = commentMarkerOffset + markerPrefix.length - point.length
        val markerLine = document.getLineNumber(pointOffset)
        val makerColumn = pointOffset - document.getLineStartOffset(markerLine)

        val elementLine = document.getLineStartOffset(markerLine - 1)
        val elementStartOffset = elementLine + makerColumn

        val elementAtMarker = file.findElementAt(elementStartOffset)
        check(elementAtMarker != null) { "Can't find an element above the point" }

        val element = PsiTreeUtil.getParentOfType(elementAtMarker, psiClass, false)
        check(element != null) { "Can't find element past element by type" }

        return element
    }
}
