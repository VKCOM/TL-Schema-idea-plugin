package com.vk.tlschema.formatter

import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.vk.tlschema.TLSchemaLanguage
import com.vk.tlschema.psi.TLSchemaTypes

class TLSchemaFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val file = formattingContext.containingFile
        val codeStyleSettings = formattingContext.codeStyleSettings

        return FormattingModelProvider.createFormattingModelForPsiFile(
            file,
            TLSchemaBlock(
                formattingContext.node,
                false,
                createSpaceBuilder(codeStyleSettings),
            ),
            codeStyleSettings,
        )
    }

    private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, TLSchemaLanguage.INSTANCE)
            .after(TLSchemaTypes.FULL_COMBINATOR_ID)
            .spaces(1)

            .afterInside(TLSchemaTypes.ARG, TLSchemaTypes.COMBINATOR_DECL)
            .spaces(1)

            .after(TLSchemaTypes.OPT_ARG)
            .spaces(1)

            .around(TLSchemaTypes.EQMARK)
            .spaces(1)

            .around(TLSchemaTypes.BOXED_TYPE_IDENT)
            .spaces(1)

            .aroundInside(TLSchemaTypes.TYPE_TERM, TLSchemaTypes.TYPE_EXPR)
            .spaces(1)

            .aroundInside(TLSchemaTypes.TERM, TLSchemaTypes.EXPR)
            .spaces(1)

            .before(TLSchemaTypes.SEMICOLON)
            .none() // перед ';' пробел не нужен

            // OK
            .withinPair(TLSchemaTypes.SQBROPEN, TLSchemaTypes.SQBRCLOSE)
            .none() // между '[' ']' пробел не нужен

            // OK
            .withinPair(TLSchemaTypes.CRBROPEN, TLSchemaTypes.CRBRCLOSE)
            .none() // между '{' '}' пробел не нужен

            // OK
            .withinPair(TLSchemaTypes.BROPEN, TLSchemaTypes.BRCLOSE)
            .none() // между '(' ')' пробел не нужен

            // OK
            .withinPair(TLSchemaTypes.LEQ, TLSchemaTypes.GEQ)
            .none() // между '<' '>' пробел не нужен

            .after(TLSchemaTypes.PERCENT)
            .none() // после '%' пробел не нужен

            .after(TLSchemaTypes.EXCMARK)
            .none() // после '!' пробел не нужен

            .beforeInside(TLSchemaTypes.COLON, TLSchemaTypes.ARG_NAMED)
            .none() // перед ':' в именованных аргументах, пробел не нужен

            .beforeInside(TLSchemaTypes.COLON, TLSchemaTypes.OPT_ARG)
            .none() // перед ':' в именованных аргументах, пробел не нужен
    }
}
