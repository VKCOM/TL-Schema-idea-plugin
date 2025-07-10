package com.vk.tl2

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.vk.tl2.parser.TL2Parser
import com.vk.tl2.psi.TL2File
import com.vk.tl2.psi.TL2Types

class TL2ParserDefinition : ParserDefinition {
    override fun createLexer(project: Project) = TL2LexerAdapter()

    override fun createParser(project: Project) = TL2Parser()

    override fun getFileNodeType() = IFileElementType(TL2Language)

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode): PsiElement = TL2Types.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider) = TL2File(viewProvider)
}
