package com.vk.tl2.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.vk.tl2.psi.TL2Integer
import com.vk.tl2.psi.TL2Visitor

class TL2NumberSizeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean) = object : TL2Visitor() {
        override fun visitInteger(o: TL2Integer) {
            val numberSize = o.text.filter { i -> i.isDigit() }.length
            if (numberSize == 8) {
                return
            }

            holder.registerProblem(
                o,
                "Длина числа должна быть 8 символов",
                ProblemHighlightType.ERROR,
            )
        }
    }
}
