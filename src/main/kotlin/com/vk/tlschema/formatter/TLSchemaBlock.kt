package com.vk.tlschema.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import com.vk.tlschema.psi.TLSchemaCombinatorDecl
import com.vk.tlschema.psi.TLSchemaTypes

class TLSchemaBlock(
    node: ASTNode,
    private val withIdent: Boolean = false,
    private val spacingBuilder: SpacingBuilder,
) : AbstractBlock(node, null, null) {
    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return node.firstChildNode == null
    }

    override fun buildChildren(): List<Block> {
        val blocks = mutableListOf<Block>()
        val parent = node.psi ?: return emptyList()

        var child = myNode.firstChildNode
        while (child != null) {
            val childType = child.elementType
            if (childType == TokenType.WHITE_SPACE) {
                child = child.treeNext
                continue
            }

            val withIdent = when {
                parent is TLSchemaCombinatorDecl -> when (childType) {
                    TLSchemaTypes.ARG -> true
                    TLSchemaTypes.OPT_ARG -> true
                    TLSchemaTypes.EQMARK -> true
                    TLSchemaTypes.COMMENT -> true

                    else -> false
                }

                else -> false
            }

            val block = TLSchemaBlock(
                node = child,
                withIdent = withIdent,
                spacingBuilder = spacingBuilder,
            )
            blocks.add(block)
            child = child.treeNext
        }

        return blocks
    }

    override fun getIndent(): Indent {
        return if (withIdent) {
            Indent.getNormalIndent()
        } else {
            Indent.getNoneIndent()
        }
    }
}
